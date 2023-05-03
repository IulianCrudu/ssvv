package LabAssiAsseProjectV02;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;


public class IncrementalIntegrationTest {
    public Service service;

    @Mock
    public StudentXMLRepo studentRepoMock;

    @Mock
    public TemaXMLRepo temaRepoMock;

    @Before
    public void setUp() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameNota = "fisiere/TestNote.xml";

//        mock student repo
        studentRepoMock = mock(StudentXMLRepo.class);
        Student stud1 = new Student("123", "Vermont", 911, "vermont@veryEvil.cme");
        Student stud2 = new Student("222", "Tirmont", 911, "tiry@veryEvil.cme");
        Student stud3 = new Student("333", "Garmont", 911, "garmeister@veryEvil.cme");
        List<Student> studlist  = new ArrayList();
        studlist.add(stud1);
        studlist.add(stud2);
        studlist.add(stud3);
        when(studentRepoMock.findAll()).thenReturn(studlist);
        when(studentRepoMock.findOne("123")).thenReturn(stud1);
        when(studentRepoMock.findOne("222")).thenReturn(stud1);

//        mock tema repo
        temaRepoMock = mock(TemaXMLRepo.class);
        Tema tema1 = new Tema("1", "cool tema 1", 8, 9);
        Tema tema2 = new Tema("2", "kinda cool tema 2", 13, 11);
        Tema tema3 = new Tema("3", "not cool tema 3", 14, 14);
        List<Tema> temaList  = new ArrayList();
        temaList.add(tema1);
        temaList.add(tema2);
        temaList.add(tema3);
        when(temaRepoMock.findAll()).thenReturn(temaList);
        when(temaRepoMock.findOne("1")).thenReturn(tema1);
        when(temaRepoMock.findOne("2")).thenReturn(tema2);


        NotaValidator notaValidator = new NotaValidator(studentRepoMock, temaRepoMock);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentRepoMock, studentValidator, temaRepoMock, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("123", "Student", 333, "email@student.com");
        service.addStudent(student);

        verify(studentRepoMock, times(1)).save(any(Student.class));
    }

    @Test
    public void testAddStudentTema() {
        Student student = new Student("123", "Student", 333, "email@student.com");
        Tema tema = new Tema("3", "not cool tema 3", 14, 14);

        service.addStudent(student);
        service.addTema(tema);

        verify(studentRepoMock, times(1)).save(any(Student.class));
        verify(temaRepoMock, times(1)).save(any(Tema.class));
    }

    @Test
    public void testAddStudentTemaGrade() {
        Student student = new Student("123", "Student", 333, "email@student.com");
        Tema tema = new Tema("3", "not cool tema 3", 14, 14);
        Nota nota = new Nota("1001", "123", "1", 8, LocalDate.now());

        service.addStudent(student);
        service.addTema(tema);
        service.addNota(nota, "testin go brrrrrrr");

        verify(studentRepoMock, times(1)).save(any(Student.class));
        verify(temaRepoMock, times(1)).save(any(Tema.class));
        verify(studentRepoMock, times(2)).findOne("123");
        verify(temaRepoMock, times(2)).findOne("1");
    }

}
