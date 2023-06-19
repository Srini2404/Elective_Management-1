package Elective_Management.Elective_Management.Controller;

import Elective_Management.Elective_Management.Entity.Student;
import Elective_Management.Elective_Management.Service.JwtUserDetailsService;
import Elective_Management.Elective_Management.Service.StudentService;
import Elective_Management.Elective_Management.config.JwtTokenUtil;
import Elective_Management.Elective_Management.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public StudentController(StudentService StudentService, JwtTokenUtil jwtTokenUtil,JwtUserDetailsService jwtUserDetailsService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.studentService = StudentService;
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudent()
    {
        return this.studentService.getAllStudents();
    }

    @GetMapping("/getbyID/{id}")
    public Student getStudentbyId(@PathVariable int id)
    {
        return this.studentService.getStudentById(id);
    }

    @PostMapping("/save")
    public Student saveStudent(@RequestBody Student Student, @RequestHeader String Authorization) {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        Student.setId(0);
        Student.setUser(user);
        return this.studentService.saveStudent(Student);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student Student, @RequestHeader String Authorization)
    {
        String username = jwtTokenUtil.getUsernameFromToken(Authorization.substring(7));
        User user = jwtUserDetailsService.getUserByUsername(username);
        Student.setUser(user);
        return this.studentService.updateStudent(Student);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable int id)
    {
        this.studentService.deleteStudentById(id);
    }
    
}
