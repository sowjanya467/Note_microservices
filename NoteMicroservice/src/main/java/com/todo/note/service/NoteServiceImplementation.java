package com.todo.note.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.todo.note.dto.Label;
import com.todo.note.dto.NoteDto;
import com.todo.note.dto.UpdateNoteDto;
import com.todo.note.model.Content;
import com.todo.note.model.Link;
import com.todo.note.model.NoteModel;
import com.todo.note.repository.LabelElasticRepository;
import com.todo.note.repository.LabelRepository;
import com.todo.note.repository.NoteElasticRepository;
import com.todo.note.repository.NoteMongoRepository;
import com.todo.note.utility.Messages;
import com.todo.note.utility.PreConditions;
import com.todo.note.utility.Utility;
//import com.todo.note.model.User;
//import com.todo.note..repository.UserRepository;
//import com.todo.note.utility.Utility;
//import com.todo.note.utility.email.SecurityConfig;
import com.todo.note.utility.exceptions.LoginExceptionHandling;
import com.todo.note.utility.exceptions.NoteExceptionHandling;
import com.todo.note.utility.exceptions.ToDoException;

/*************************************************************************************************************
 *
 * purpose:Note Service implementation
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/
@Service
public class NoteServiceImplementation implements NoteService, ServiceFeign {
	@Autowired
	NoteMongoRepository noteRepository;

	@Autowired
	ModelMapper mapper;

	@Autowired
	private LabelRepository labelRepository;
	@Autowired
	private NoteElasticRepository noteElasticRepo;
	@Autowired
	private LabelElasticRepository labelElasticRepo;
	@Autowired
	Messages messages;
	@Autowired
	ServiceFeign service;
	Utility util = new Utility();
	// @Autowired
	// AwsService AmazonServices;
	private ElasticService eservice;

	Timer timer;
	// Utility utility = new Utility();
	// SecurityConfig security = new SecurityConfig();
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	LocalDateTime now = LocalDateTime.now();
	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImplementation.class);

	/**
	 * method to create note
	 * 
	 * @param note
	 * @param jwtToken
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws UserExceptionHandling
	 * @throws ToDoException
	 * @throws IOException
	 */
	@Override
	public String createNote(NoteDto note, String userId) throws LoginExceptionHandling, ToDoException, IOException {
		logger.info(messages.get("216"));
		// Optional<User> user = userRepository.findById(userId);

		// PreConditions.isPresentInDb(user.isPresent(), messages.get("105"));
		PreConditions.checkNotNull(note.getTitle(), messages.get("106"));
		PreConditions.checkNotNull(note.getContent(), messages.get("107"));

		if (note.getLabel() != null) {
			logger.debug(messages.get("222"));

		}
		logger.info("mapping...");
		NoteModel newNote = mapper.map(note, NoteModel.class);
		if (!note.getContent().equals("")) {
			String noteDescription = note.getContent();
			newNote.setContent(setcontent(noteDescription));
		}
		newNote.setUserId(userId);
		String id = newNote.setId(sdf.format(new Date()));

		/*
		 * List<String> imagePath=new ArrayList<String>(); List<String> image=new
		 * ArrayList<>(); imagePath=note.getImage(); for(int i=0;i<imagePath.size();i++)
		 * { String imagelink=
		 * AmazonServices.storeImages("bridgelabz-todo-storage",userId, id,
		 * imagePath.get(i)); image.add(imagelink);
		 * 
		 * 
		 * } newNote.setImage(image);
		 */

		newNote.setCreatedAt(dtf.format(now));
		System.out.println(dtf.format(now));
		System.out.println(dtf.format(now));
		newNote.setEditedAt(dtf.format(now));
		System.out.println(newNote);
		// eservice.insertnote(newNote);

		System.out.println(newNote);
		noteRepository.save(newNote);
		// newNote = noteElasticRepo.save(newNote);

		logger.info(messages.get("229"));

		return id;
	}

	public static Content setcontent(String con) throws IOException {
		ContentScrappingImpl csp = new ContentScrappingImpl();

		Content contents = new Content();
		List<String> stContent = new ArrayList<>();
		List<Link> links = new ArrayList<>();

		String[] contentArray = con.split(" ");
		for (int i = 0; i < contentArray.length; i++) {
			if (contentArray[i].startsWith("https://") || contentArray[i].startsWith("http://")) {
				Link link = new Link();
				link.setTitle(csp.getTitle(contentArray[i]));
				link.setImage(csp.getImage(contentArray[i]));
				link.setUrl(csp.getUrl(contentArray[i]));
				links.add(link);
			} else if (!contentArray[i].equals("")
					&& (!contentArray[i].startsWith("https://") || !contentArray[i].startsWith("http://"))) {
				stContent.add(contentArray[i]);
			}
		}

		contents.setContents(stContent);
		contents.setLinks(links);

		return contents;

	}

	/**
	 * method to update note
	 * 
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param id
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 */

	@Override
	public void updateNote(String userId, String id, UpdateNoteDto notes) throws LoginExceptionHandling, ToDoException {
		logger.info(messages.get("217"));

		logger.debug(messages.get("230"));
		Optional<NoteModel> note = noteElasticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		logger.debug(messages.get("231"));
		NoteModel note1 = mapper.map(notes, NoteModel.class);
		note1.setContent(notes.getContent());
		note1.setTitle((notes.getTitle()));
		note1.setEditedAt(dtf.format(now));
		noteRepository.save(note1);
		noteElasticRepo.save(note1);

	}

	/**
	 * method to delete note
	 * 
	 * @param title
	 * @param token
	 * @param note
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws NoteExceptionHandling
	 */
	public void deleteNote(String id, String userId)
			throws LoginExceptionHandling, NoteExceptionHandling, ToDoException {
		logger.info(messages.get("232"));
		// Optional<User> user = userRepository.findById(userId);
		Optional<NoteModel> note = noteElasticRepo.findById(id);

		// PreConditions.isPresentInDb(user.isPresent(), messages.get("105"));
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));

		if (note.get().isTrash() == false) {
			logger.error(messages.get("233"));
			throw new NoteExceptionHandling(messages.get("110"));
		}
		noteRepository.deleteById(id);
		noteElasticRepo.deleteById(id);

	}

	/***/
	/**
	 * archive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void archiveNote(String id) throws ToDoException {

		Optional<NoteModel> note = noteElasticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		PreConditions.isPresentInDb(!note.get().isArchive(), messages.get("111"));
		NoteModel notes = note.get();
		notes.setArchive(true);
		notes.setEditedAt(dtf.format(now));

		noteRepository.save(notes);
		noteElasticRepo.save(notes);

		logger.info("note archived");
	}

	/**
	 * method to set pin set the pin
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void setPin(String id) throws ToDoException {
		Optional<NoteModel> note = noteElasticRepo.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		logger.debug(messages.get("109"));
		PreConditions.isPresentInDb(!note.get().isPin(), messages.get("112"));
		NoteModel notes = note.get();
		notes.setPin(true);
		notes.setEditedAt(dtf.format(now));
		noteRepository.save(notes);
		noteElasticRepo.save(notes);
		logger.info(messages.get("204"));

	}

	/**
	 * trash the note
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void trashNote(String id) throws ToDoException {
		Optional<NoteModel> note = noteElasticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("105"));
		PreConditions.isPresentInDb(!note.get().isTrash(), messages.get("113"));

		NoteModel notes = note.get();
		notes.setTrash(true);
		notes.setEditedAt(dtf.format(now));
		noteElasticRepo.save(notes);
		noteRepository.save(notes);

	}

	/**
	 * restore the trashed note
	 * 
	 * @param id
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void restoreTrashedNote(String id) throws ToDoException {
		Optional<NoteModel> note = noteRepository.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), messages.get("105"));
		PreConditions.isPresentInDb(note.get().isTrash(), messages.get("115"));

		NoteModel notes = note.get();
		notes.setTrash(false);
		notes.setEditedAt(dtf.format(now));

		noteRepository.save(notes);
	}

	/**
	 * display all trashed notes
	 * 
	 * @param request
	 * @return noteList
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewTrashNotes(String userId) throws ToDoException {
		// Optional<User> user = userRepository.findById(userId);
		// PreConditions.isPresentInDb(user.isPresent(), messages.get("105"));

		List<NoteModel> noteList = noteElasticRepo.findByUserId(userId);
		List<NoteModel> viewNotes = new ArrayList<>();
		noteList.stream().filter(noteLists -> noteLists.isTrash() == true).forEach(n -> viewNotes.add(n));
		return viewNotes;

	}

	/**
	 * display all archived notes
	 * 
	 * @param request
	 * @return noteList
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewArchivedNotes(String userId) throws ToDoException {
		// Optional<User> user = userRepository.findById(userId);
		// PreConditions.isPresentInDb(user.isPresent(), messages.get("105"));

		List<NoteModel> noteList = noteElasticRepo.findByUserId(userId);
		List<NoteModel> viewNotes = new ArrayList<>();
		noteList.stream().filter(noteLists -> noteLists.isArchive() == true).forEach(n -> viewNotes.add(n));

		return viewNotes;

	}

	/**
	 * method to display all the notes
	 * 
	 * @param token
	 * @return noteList
	 * @throws ToDoException
	 */
	@Override
	public List<NoteModel> viewNotes(String userId) throws ToDoException {

		List<NoteModel> noteList = noteElasticRepo.findByUserId(userId);
		List<NoteModel> viewNotes = new ArrayList<>();
		noteList.stream().filter(noteLists -> noteLists.isTrash() == false).forEach(n -> viewNotes.add(n));

		return viewNotes;

	}

	/**
	 * 
	 * @param noteId
	 * @param token
	 * @return
	 * @throws ToDoException
	 */

	/*
	 * @Override public ViewNoteDto viewNote(String noteId, String userId) throws
	 * ToDoException { Optional<NoteModel> note = validateNoteAndUser(noteId,
	 * userId); if (note.get().isTrash()) { throw new
	 * ToDoException(messages.get("115")); } return mapper.map(note.get(),
	 * ViewNoteDto.class);
	 * 
	 * }
	 */

	/**
	 * unarchive the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void unarchiveNote(String id) throws ToDoException {

		Optional<NoteModel> note = noteElasticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		PreConditions.isPresentInDb(note.get().isArchive(), messages.get("116"));
		NoteModel notes = note.get();
		notes.setArchive(false);
		notes.setEditedAt(dtf.format(now));
		noteRepository.save(notes);
		noteElasticRepo.save(notes);
		logger.info("note unarchived");
	}

	/**
	 * unpin the note
	 * 
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void unPin(String id) throws ToDoException {

		Optional<NoteModel> note = noteElasticRepo.findById(id);

		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		PreConditions.isPresentInDb(note.get().isPin(), messages.get("117"));
		NoteModel notes = note.get();
		notes.setPin(false);
		notes.setEditedAt(dtf.format(now));
		noteRepository.save(notes);
		noteElasticRepo.save(notes);
		logger.info("note is pinned");
	}

	/**
	 * set reminder
	 * 
	 * @param id
	 * @param token
	 * @param reminderTime
	 * @return
	 * @throws ToDoException
	 * @throws ParseException
	 */
	@Override
	public void remainder(String userId, String noteId, String reminderTime) throws ToDoException, ParseException {

		System.out.println("remainder task");
		List<NoteModel> noteList = noteRepository.findByUserId(userId);

		for (NoteModel note : noteList) {
			if (note.getId().equals(noteId)) {

				System.out.println("note present");
				Date reminder = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(reminderTime);
				long timeDif = reminder.getTime() - new Date().getTime();
				timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						logger.info("Reminder :" + note.getId().toString());
						System.out.println("Reminder :" + note.getId().toString());
					}
				}, timeDif);
			}
		}
	}

	/**
	 * create new label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void createNewLabel(String labelName, String userId, String noteId) throws ToDoException {

		logger.info(messages.get("243"));
		// Optional<User> user = userRepository.findById(userId);
		// PreConditions.isPresentInDb(user.isPresent(), messages.get("105"));

		Optional<NoteModel> note = noteElasticRepo.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		List<NoteModel> noteList = noteElasticRepo.findByUserId(userId);
		Optional<Label> labelFound = labelRepository.findByLabelName(labelName);
		PreConditions.isPresentInDb(!labelFound.isPresent(), messages.get("120"));
		Label label = new Label();

		for (NoteModel n : noteList) {
			if (n.getId().equals(noteId)) {
				logger.info(messages.get("212"));
				label.setLabelName(labelName);
				label.setUserid(userId);
				labelRepository.save(label);
				labelElasticRepo.save(label);
				NoteModel notelabel = mapper.map(label, NoteModel.class);
				n.getLabel().add(label);
				n.setEditedAt(dtf.format(now));

				noteRepository.save(n);

				System.out.println(notelabel);

			}

		}

	}

	/**
	 * add new label to note
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void addLabel(String labelId, String userId, String noteId) throws ToDoException {

		logger.info(messages.get("234"));

		Optional<NoteModel> note = noteElasticRepo.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));

		// Check if note has a list of labels or not, if not ,then create a new List
		if (note.get().getLabel() == null) {
			List<Label> newLabelList = new ArrayList<>();
			note.get().setLabel(newLabelList);
		}

		// check if label is present in labelRepository
		Optional<Label> labelFound = labelRepository.findById(labelId);
		Label label = new Label();

		for (int i = 0; i < note.get().getLabel().size(); i++) {
			if (labelId.equals(note.get().getLabel().get(i).getLabelId())) {

				logger.error(messages.get("235"));
				throw new ToDoException(messages.get("120"));
			}
		}

		System.out.println("labelid" + labelFound.get().getLabelId());
		label.setLabelId(labelFound.get().getLabelId());
		label.setLabelName(labelFound.get().getLabelName());
		note.get().getLabel().add(label);
		note.get().setEditedAt(dtf.format(now));
		noteRepository.save(note.get());
		noteElasticRepo.save(note.get());

	}

	@Override
	public void createLabel(String labelName, String userId) throws ToDoException {

		logger.info(messages.get("243"));
		/// boolean t=util.isValidateAllFields(labelName);
		// System.out.println("xssxadsacaj"+t);
		if (!util.isValidateAllFields(labelName)) {
			PreConditions.checkNotNull(labelName, "label name should not be empty");
			Optional<Label> labelFound = labelRepository.findByLabelName(labelName);
			PreConditions.isPresentInDb(!labelFound.isPresent(), messages.get("120"));
		}
		Label label = new Label();
		logger.info(messages.get("212"));
		label.setLabelName(labelName);
		label.setUserid(userId);
		labelRepository.save(label);
		labelElasticRepo.save(label);

	}

	/**
	 * delete label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void deleteLabel(String labelId, String userId) throws ToDoException {

		logger.info(messages.get("242"));
		Optional<Label> labelFound = labelRepository.findById(labelId);
		if (labelFound == null) {
			logger.error(messages.get("236"));
			throw new ToDoException(messages.get("119"));
		}
		labelRepository.deleteById(labelId);
		List<NoteModel> noteList = noteRepository.findByUserIdandLabel(userId, labelId);

		for (int i = 0; i < noteList.size(); i++) {

			for (int j = 0; j < noteList.get(i).getLabel().size(); j++) {

				if (labelId.equals(noteList.get(i).getLabel().get(j).getLabelId())) {
					logger.debug(messages.get("237"));
					noteList.get(i).getLabel().remove(j);
					NoteModel note = noteList.get(i);
					logger.info(messages.get("238"));
					noteElasticRepo.save(note);
					break;
				}

			}

		}
	}

	/**
	 * update label
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public void updateLabel(String labelId, String userId, String newLabelName) throws ToDoException {
		logger.info(messages.get("239"));
		Optional<Label> labelFound = labelRepository.findById(labelId);
		PreConditions.isPresentInDb(labelFound.isPresent(), messages.get("118"));
		labelFound.get().setLabelName(newLabelName);
		labelRepository.save(labelFound.get());

		List<NoteModel> noteList = noteRepository.findByUserIdandLabel(userId, labelId);
		// noteList.stream()

		for (int i = 0; i < noteList.size(); i++) {

			if (noteList.get(i).getLabel() == null) {
				continue;
			}
			for (int j = 0; j < noteList.get(i).getLabel().size(); j++) {

				if (labelId.equals(noteList.get(i).getLabel().get(j).getLabelId())) {
					noteList.get(i).getLabel().get(j).setLabelName(newLabelName);
					NoteModel note = noteList.get(i);
					logger.info(messages.get("240"));

					noteRepository.save(note);
					noteElasticRepo.save(note);
					break;
				}

			}
		}

	}

	/**
	 * displaying labels
	 * 
	 * @param labelName
	 * @param token
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@Override
	public List<Label> viewLabels(String userId) throws ToDoException {
		logger.info(messages.get("241"));

		List<Label> labelList = labelRepository.findAllByUserid(userId);
		return labelList;

	}

	/**
	 * sorting notes by title
	 * 
	 */

	@Override
	public List<NoteModel> sortByTitle(String userId) {
		List<NoteModel> notes = noteElasticRepo.findByUserId(userId);
		List<NoteModel> sortedNotes = notes.stream()
				.sorted((t1, t2) -> t1.getTitle().compareToIgnoreCase(t2.getTitle())).collect(Collectors.toList());
		return sortedNotes;

	}

	@Override
	public List<NoteModel> sortByNoteId(String userId) {
		List<NoteModel> notes = noteElasticRepo.findByUserId(userId);

		List<NoteModel> sortedNotes = notes.parallelStream().sorted((i1, i2) -> i1.getId().compareTo(i2.getId()))
				.collect(Collectors.toList());
		return sortedNotes;

	}

	@Override
	public void addImage(String noteId, MultipartFile imagePath, String userId)
			throws ToDoException, AmazonServiceException, SdkClientException, IOException {
		logger.info(messages.get("243"));

		Optional<NoteModel> note = noteElasticRepo.findById(noteId);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("109"));
		List<NoteModel> noteList = noteElasticRepo.findByUserId(userId);

		System.out.println("image service starts");

		for (NoteModel n : noteList) {
			if (n.getId().equals(noteId)) {
				logger.info(messages.get("212"));

				List<String> image = new ArrayList<>();
				//// String path = AmazonServices.storeImages(userId, noteId, imagePath);
				//// image.add(path);

				n.setImage(image);

				noteRepository.save(n);

			}
		}

	}

	/*
	 * @Override public String CreateBucket(String bucketName) {
	 * service.CreateBucket(bucketName); return bucketName; }
	 */

	public List<?> getAllUsers() {
		List<?> users = service.getAllUsers();
		return users;
	}

}
