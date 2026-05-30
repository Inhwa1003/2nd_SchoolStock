package com.school.schoolstock.domain.student.service;

import com.school.schoolstock.domain.student.repository.StudentRepository;
import com.school.schoolstock.domain.student.vo.Students;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;
    @Override
    public String getIdCheck(String studentId) {
        return studentRepository.getIdCheck(studentId);
    }

    @Override
    public boolean setMember(Students students) {
        return studentRepository.setMember(Students.builder()
                .studentId(students.getStudentId())
                .password(students.getPassword())
                .name(students.getName())
                .grade(students.getGrade())
                .className(students.getClassName())
                .studentNumber(students.getStudentNumber()).build());
    }

    @Override
    public Students login(String studentId, String password) {
        return studentRepository.login(studentId, password);
    }

    @Override
    public int getMyValue(String studentId) {
        return studentRepository.getMyValue(studentId);
    }

    @Override
    public int getMyPoint(String studentId) {
        return studentRepository.getMyPoint(studentId);
    }

    @Override
    public int getTotalProfit(String studentId) {
        return studentRepository.getTotalProfit(studentId);
    }

    @Override
    public int getMyCouponAmount(String studentId) {
        return studentRepository.getMyCouponAmount(studentId);
    }

    @Override
    public int getMyStockAmount(String studentId, int stockNo) {
        return studentRepository.getMyStockAmount(studentId, stockNo);
    }

    @Override
    public int getAveragePoint(String studentId, int stockNo) {
        return studentRepository.getAveragePoint(studentId, stockNo);
    }

    @Override
    public int getPurchasePoint(String studentId, int stockNo) {
        return studentRepository.getPurchasePoint(studentId, stockNo);
    }

    @Override
    public List<Integer> getMyStockNos(String studentId) {
        return studentRepository.getMyStockNos(studentId);
    }

    @Override
    public int getStockProfit(String studentId, int stockNo) {
        return studentRepository.getStockProfit(studentId, stockNo);
    }

    @Override
    public List<Map<String, Object>> getTotalMyOrder(String studentId, int stockNo) {
        return studentRepository.getTotalMyOrder(studentId, stockNo);
    }

    @Override
    public boolean setStudentPointUp(String studentId, int totalPoint) {
        return studentRepository.setStudentPointUp(studentId, totalPoint);
    }

    @Override
    public boolean setStudentPointDown(String studentId, int totalPoint) {
        return studentRepository.setStudentPointDown(studentId, totalPoint);
    }

    @Override
    public List<Map<String, Object>> getMyPointHistoryList(String studentId) {
        return studentRepository.getMyPointHistoryList(studentId);
    }

    @Override
    public boolean setStudentAssets(String studentId, int point) {
        return studentRepository.setStudentAssets(studentId, point);
    }

    @Override
    public List<Map<String, Object>> getMyCouponList(String studentId) {
        return studentRepository.getMyCouponList(studentId);
    }
}
