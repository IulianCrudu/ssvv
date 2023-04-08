package LabAssiAsseProjectV02;

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
import validation.ValidationException;

public class TemaTest {
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
        TemaTest.service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test(expected=ValidationException.class)
    public void testIDEmpty() {
        Tema tema = new Tema("1", "Descriere", 10, 9);
        tema.setID("");

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testIDNull() {
        Tema tema = new Tema("1", "Descriere", 10, 9);
        tema.setID(null);

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testDescriereEmpty() {
        Tema tema = new Tema("1", "", 10, 9);
        tema.setID("2");

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testDeadlineLessThan1() {
        Tema tema = new Tema("1", "Descriere", 0, 9);
        tema.setID("2");

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testDeadlineHigherThan14() {
        Tema tema = new Tema("1", "Descriere", 0, 9);
        tema.setID("2");

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testPrimireLessThan1() {
        Tema tema = new Tema("1", "Descriere", 10, 0);
        tema.setID("2");

        service.addTema(tema);
    }

    @Test(expected=ValidationException.class)
    public void testPrimireHigherThan14() {
        Tema tema = new Tema("1", "Descriere", 10, 16);
        tema.setID("2");

        service.addTema(tema);
    }

    @Test
    public void testValidTema() {
        Tema tema = new Tema("1", "Descriere", 10, 12);
        tema.setID("2");

        service.addTema(tema);
    }
}
