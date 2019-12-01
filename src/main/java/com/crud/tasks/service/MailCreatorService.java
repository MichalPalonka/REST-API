package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private DbService dbService;

    public String buildTrelloCardEmil(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can mange your tasks.");
        functionality.add("Provides connection with Trello Account.");
        functionality.add("Application allows sending tasks to Trello.");

        Context context = contextCreator(message);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    private Context contextCreator(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://michalpalonka.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("show_button", false);
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getAdminCompany());
        context.setVariable("goodbye_message", "See You soon!");
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        return context;
    }

    public String buildTaskCountEmial(String message) {
        List<String> tasks = new ArrayList<>();
        dbService.getAllTasks().stream().forEach(task -> tasks.add(task.getTitle()));

        Context context = contextCreator(message);
        context.setVariable("tasks_list", tasks);
        return templateEngine.process("mail/created-task-count-mail", context);
    }

}
