package LabAssiAsseProjectV02;

import domain.Student;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class StudentTest {
    public static Service service;

    @BeforeClass
    public static void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/TestStudenti.xml";
        String filenameTema = "fisiere/TestTeme.xml";
        String filenameNota = "fisiere/TestNote.xml";

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        StudentTest.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testValidAddStudent() {
        Student student = new Student("123", "Studentul Bravo", 333, "email@student.com");
        service.addStudent(student);

        Student foundStudent = service.findStudent("123");
        Assert.assertEquals(student.getID(), foundStudent.getID());
        Assert.assertEquals(student.getEmail(), foundStudent.getEmail());
        Assert.assertEquals(student.getNume(), foundStudent.getNume());
    }

    @Test(expected=ValidationException.class)
    public void testInvalidIdAddStudent() {
        Student student = new Student("", "Studentul rau", 333, "email2@student.com");
        service.addStudent(student);
    }

    @Test(expected=ValidationException.class)
    public void testInvalidNameAddStudent() {
        Student student = new Student("22", "", 333, "email2@student.com");
        service.addStudent(student);
    }

    @Test(expected=ValidationException.class)
    public void testInvalidGroupAddStudent() {
        Student student = new Student("22", "Studentul rau", -22, "email2@student.com");
        service.addStudent(student);
    }
}
