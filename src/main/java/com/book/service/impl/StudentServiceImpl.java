package com.book.service.impl;

import com.book.dao.StudentMapper;
import com.book.entity.Student;
import com.book.service.StudentService;
import com.book.util.MybatisUntil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public List<Student> getStudent() {
        try (SqlSession sqlSession = MybatisUntil.getSession(true)) {
            StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
            return mapper.getStudentList();
        }
    }
}
