package LabAssiAsseProjectV02;

import domain.Nota;
import domain.Student;
import domain.Tema;
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

import java.util.Date;
import java.time.LocalDate;


public class IntegrationTest {
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
        IntegrationTest.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("123", "Student", 333, "email@student.com");
        service.addStudent(student);

        Student foundStudent = service.findStudent("123");
        Assert.assertEquals(student.getID(), foundStudent.getID());
        Assert.assertEquals(student.getEmail(), foundStudent.getEmail());
        Assert.assertEquals(student.getNume(), foundStudent.getNume());
    }

    @Test
    public void testAddTema() {
        Tema tema = new Tema("2", "Descriere2", 6, 12);
        tema.setID("12");

        service.addTema(tema);
    }

    @Test
    public void testAddGrade() {
        Nota nota = new Nota("1", "123", "12", 8.5, LocalDate.now());
        service.addNota(nota, "Foarte bine");
    }

    @Test
    public void testBigBangIntegration() {
        Student student = new Student("333", "Student123", 343, "email123@student.com");
        service.addStudent(student);

        Tema tema = new Tema("3", "Descriere3", 6, 12);
        tema.setID("55");

        service.addTema(tema);

        Nota nota = new Nota("3", "333", "55", 8.5, LocalDate.now());
        service.addNota(nota, "Foarte bine");
    }
}
