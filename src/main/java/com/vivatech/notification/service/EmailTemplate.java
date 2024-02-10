package com.vivatech.notification.service;

import com.vivatech.dto.StudentAcademicDetail;
import com.vivatech.dto.StudentDto;
import com.vivatech.model.ums.*;
import com.vivatech.notification.dto.MessageEnvelope;
import com.vivatech.notification.exception.UMSNotificationException;
import com.vivatech.repository.ums.*;
import com.vivatech.utility.UMSEnums;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
public class EmailTemplate {

    @Value("${registration.slip.url}")
    private String regSlipUrlPrefix;

    @Value("${resource.url}")
    private String resourceUrl;

    @Autowired
    private Configuration config;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentsRepository departmentsRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private ShiftsRepository shiftsRepository;

    @Autowired
    private SectionsRepository sectionsRepository;

    @Autowired
    private SemestersRepository semestersRepository;

    @Autowired
    private SemcoRepository semcoRepository;

    public String sendLoginDetailEmailToStudent(MessageEnvelope envelope) {
        String emailBody = null;
        Student studentInfo = studentRepository.findByAdmissionNo(envelope.getUserId());
        Departments department = departmentsRepository.findById(studentInfo.getAllottedDepartment()).orElseThrow(() -> new UMSNotificationException("Department not found"));
        Batch batch = batchRepository.findById(studentInfo.getBatchId()).orElseThrow(() -> new UMSNotificationException("Batch not found"));
        Semesters semesters = semestersRepository.findById(studentInfo.getSemesterid()).orElseThrow(() -> new UMSNotificationException("Semester not found"));
        Sections sections = null;
        if (studentInfo.getSectionId() != null){
            sections = sectionsRepository.findById(studentInfo.getSectionId()).orElseThrow(() -> new UMSNotificationException("Section not found"));
        }
        try {
            URL regSlipUrl = new URL(regSlipUrlPrefix + "/admission/registrationslip?registrationNo=" + studentInfo.getRegistrationNo());
            config.setClassForTemplateLoading(this.getClass(), "/template");

            Template t = config.getTemplate("email.html");
            Map<String, Object> model = new HashMap<>();
            model.put("regno", studentInfo.getRegistrationNo());
            model.put("name", studentInfo.getFirstName());
            model.put("pwd", "12345");
            model.put("dept", department.getName());
            model.put("batch", batch.getName());
            model.put("sem", semesters.getName());
            model.put("sec", sections != null ? sections.getName() : "NA");
            model.put("regSlipUrl", regSlipUrl);
            model.put("resource", resourceUrl);
            emailBody = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e){
            log.error("Exception: ", e);
        }
        return emailBody;
    }

    public String sendRegistrationDetailToStudent(MessageEnvelope envelope) {
        String emailBody = null;
        Student studentInfo = studentRepository.findByAdmissionNo(envelope.getUserId());
        Departments department = departmentsRepository.findById(studentInfo.getAllottedDepartment()).orElseThrow(() -> new UMSNotificationException("Department not found"));
        Batch batch = batchRepository.findById(studentInfo.getBatchId()).orElseThrow(() -> new UMSNotificationException("Batch not found"));
        Semco semco = semcoRepository.findById(studentInfo.getSemesterid()).orElseThrow(() -> new UMSNotificationException("Semester not found"));
        Sections sections = null;
        if (studentInfo.getSectionId() != null){
            sections = sectionsRepository.findById(studentInfo.getSectionId()).orElseThrow(() -> new UMSNotificationException("Section not found"));
        }
        try {
            config.setClassForTemplateLoading(this.getClass(), "/template");

            Template t = config.getTemplate("registration-email.html");
            Map<String, Object> model = new HashMap<>();
            model.put("admNo", studentInfo.getAdmissionNo());
            model.put("name", studentInfo.getFirstName());
            model.put("dept", department.getName());
            model.put("batch", batch.getName());
            model.put("sem", semco.getName());
            model.put("sec", sections != null? sections.getName() : "NA");
            model.put("resource", resourceUrl);
            emailBody = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        } catch (Exception e){
            log.error("Exception: ", e);
        }
        return emailBody;
    }

}
