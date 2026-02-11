package com.institute.admin.services;

import com.institute.admin.model.Course;
import com.institute.admin.model.Student;
import com.institute.admin.model.Message;
import com.institute.admin.repository.CourseRepository;
import com.institute.admin.repository.StudentRepository;
import com.institute.admin.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public AdminService(CourseRepository courseRepository,
                        StudentRepository studentRepository,
                        MessageRepository messageRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.messageRepository = messageRepository;
    }

    // =========================
    // COURSE MANAGEMENT
    // =========================

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) {
        if (course.getName() == null || course.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty");
        }
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {

            if (updatedCourse.getName() != null &&
                !updatedCourse.getName().trim().isEmpty()) {
                course.setName(updatedCourse.getName());
            }

            if (updatedCourse.getDescription() != null) {
                course.setDescription(updatedCourse.getDescription());
            }

            return courseRepository.save(course);

        }).orElseThrow(() ->
            new RuntimeException("Course not found with id " + id)
        );
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id " + id);
        }
        courseRepository.deleteById(id);
    }

    // =========================
    // STUDENT MANAGEMENT
    // =========================

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new IllegalArgumentException("Student name required");
        }
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            throw new IllegalArgumentException("Student email required");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {

        return studentRepository.findById(id).map(student -> {

            if (updatedStudent.getName() != null &&
                !updatedStudent.getName().isBlank()) {
                student.setName(updatedStudent.getName());
            }

            if (updatedStudent.getEmail() != null &&
                !updatedStudent.getEmail().isBlank()) {
                student.setEmail(updatedStudent.getEmail());
            }

            return studentRepository.save(student);

        }).orElseThrow(() ->
            new RuntimeException("Student not found with id " + id)
        );
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }

    // =========================
    // MESSAGE MANAGEMENT
    // =========================

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public Message addMessage(Message message) {

        if (message.getSenderName() == null ||
            message.getSenderName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sender name cannot be empty");
        }

        if (message.getEmail() == null ||
            message.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (message.getContent() == null ||
            message.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty");
        }

        return messageRepository.save(message);
    }

    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new RuntimeException("Message not found with id " + id);
        }
        messageRepository.deleteById(id);
    }
}
