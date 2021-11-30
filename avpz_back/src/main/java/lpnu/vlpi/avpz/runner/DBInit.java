package lpnu.vlpi.avpz.runner;

import lpnu.vlpi.avpz.dao.*;
import lpnu.vlpi.avpz.helper.EncryptionHelper;
import lpnu.vlpi.avpz.model.*;
import lpnu.vlpi.avpz.model.enums.Level;
import lpnu.vlpi.avpz.model.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DBInit implements CommandLineRunner {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EncryptionHelper encryptionHelper;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start");
        List<ModuleModel> modules = initModules();
        List<UserModel> users = initUsers();
        List<TopicModel> topicModels = initTopics(modules);
        List<TaskModel> tasks = initTasks(topicModels);
        List<CategoryModel> categories = initCategories(tasks);
        List<VariantModel> variants = initVariants(categories);
    }

    private List<StatisticModel> initStatistic(List<UserModel> users, List<TaskModel> tasks) {
        List<StatisticModel> statisticModels = new ArrayList<>();
        StatisticModel statisticModel = new StatisticModel();
        UserModel userModel = users.get(0);
        statisticModel.setAverageMark(1.2f);
        statisticModel.setAverageTime(1.2f);
        statisticModel.setTotalTaskComplete(22);
        statisticModel.setUser(userModel);
        statisticModel.setUid("4241241");
        statisticModels.add(statisticRepository.save(statisticModel));
        return statisticModels;
    }

    private List<VariantModel> initVariants(List<CategoryModel> categories) {
        List<VariantModel> variantModels = new ArrayList<>();
//        (1, 1, 'Calculator should be able to raise numbers to a power', 1, 1, 'It refers to something a calculator can do, hence a "function"'),
//        (2, 2, 'Calculator need to have a "Raise to the power button" with a "^" symbol', 1, 2, 'It describes what user can do with the interface, so an UI Requirement'),
//        (3, 3, 'An operation should take no more than 3ms', 1, 3, 'This requirement describes how fast the calculator runs and "performs"'),
//        (4, 4, 'User should be able to enter negative numbers', 1, 1, 'This describes a function a calculator provides, so it\"s Functional'),
//        (5, 5, 'Numbers with fractions should have 5 values after the comma', 1, 2, 'Ignoring 6+ numbers after comma will obviously make the calculations wrong, the requirement refers to displaying of the number on UI '),
//        --||--
//                (6, 6, 'UI background should be red', 2, 5, 'Red is not a definite color, so its unclear what shade of red to use'),
//        (7, 7, 'Program must do fast calculations ', 2, 5, 'Requirement is unclear since "fast" is no a set time window'),
//        (8, 8, 'Program must work on Windows 10 and Linux Mint', 2, 4, 'The requirement is clear and understood'),
//        (9, 9, 'Program should allow user to save the data in a file', 2, 4, 'Although it"s unclear what data and what file, in a context it is valid'),
//        (10, 10, 'User should be able to close the program', 2, 5, 'Requirements like these are a given, and should not be documented'),
//        --||--
//                (11, 11, 'Program should allow user to export data in different file formats (pdf., docx., png., etc.)', 3, 7, 'Since it does not restrict user from program functionality, it is medium'),
//        (12, 12, 'User can open an already existing file', 3, 6, 'This functionality will be user a lot, so high priority'),
//        (13, 13, 'The name of the program should be "Bets Text Editor"', 3, 8, 'The requirement is small and inconsequential'),
//        (14, 14, 'User can insert png files in the text', 3, 6, 'This functionality will be user a lot, so high priority');

        VariantModel variantModel111 = new VariantModel();
        variantModel111.setUid("1");
        variantModel111.setDisplayName("Calculator should be able to raise numbers to a power");
        variantModel111.setCategory(categories.get(0));
        variantModel111.setTask(variantModel111.getCategory().getTask());
        variantModel111.setTooltip("It refers to something a calculator can do, hence a \"function\"");
        variantModel111.setCreateTime(new Date());
        variantModel111.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel111));

        VariantModel variantModel112 = new VariantModel();
        variantModel112.setUid("2");
        variantModel112.setDisplayName("Calculator need to have a \"Raise to the power button\" with a \"^\" symbol");
        variantModel112.setCategory(categories.get(1));
        variantModel112.setTask(variantModel111.getCategory().getTask());
        variantModel112.setTooltip("It describes what user can do with the interface, so an UI Requirement");
        variantModel112.setCreateTime(new Date());
        variantModel112.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel112));

        VariantModel variantModel121 = new VariantModel();
        variantModel121.setUid("3");
        variantModel121.setDisplayName("An operation should take no more than 3ms");
        variantModel121.setCategory(categories.get(2));
        variantModel121.setTask(variantModel121.getCategory().getTask());
        variantModel121.setTooltip("This requirement describes how fast the calculator runs and \"performs\"");
        variantModel121.setCreateTime(new Date());
        variantModel121.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel121));

        VariantModel variantModel122 = new VariantModel();
        variantModel122.setUid("4");
        variantModel122.setDisplayName("User should be able to enter negative numbers");
        variantModel122.setCategory(categories.get(0));
        variantModel122.setTask(variantModel122.getCategory().getTask());
        variantModel122.setTooltip("This describes a function a calculator provides, so it\"s Functional");
        variantModel122.setCreateTime(new Date());
        variantModel122.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel122));

        VariantModel variantModel131 = new VariantModel();
        variantModel131.setUid("5");
        variantModel131.setDisplayName("Numbers with fractions should have 5 values after the comma");
        variantModel131.setCategory(categories.get(1));
        variantModel131.setTask(variantModel131.getCategory().getTask());
        variantModel131.setTooltip("Ignoring 6+ numbers after comma will obviously make the calculations wrong, the requirement refers to displaying of the number on UI ");
        variantModel131.setCreateTime(new Date());
        variantModel131.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel131));


        return variantModels;
    }
    private List<TaskModel> initTasks(List<TopicModel> topics) {
        List<TaskModel> tasks = new ArrayList<>();

        TaskModel task1 = new TaskModel();
        task1.setUid("343242");
        task1.setDisplayName("Recognise different types of requirements");
        task1.setLevel(Level.EASY);
        task1.setTopic(topics.get(0));
        task1.setModule(task1.getTopic().getModule());
        task1.setCreateTime(new Date());
        task1.setModifiedTime(new Date());
        task1.setExecutionTime(30);
        tasks.add(taskRepository.save(task1));

        TaskModel task2 = new TaskModel();
        task2.setUid("343243");
        task2.setDisplayName("Valid/Invalid requirements");
        task2.setLevel(Level.NORMAL);
        task2.setTopic(topics.get(0));
        task2.setModule(task2.getTopic().getModule());
        task2.setCreateTime(new Date());
        task2.setModifiedTime(new Date());
        task2.setExecutionTime(30);
        tasks.add(taskRepository.save(task2));


        TaskModel task3 = new TaskModel();
        task3.setUid("343244");
        task3.setDisplayName("How to prioritise requirements");
        task3.setLevel(Level.HARD);
        task3.setTopic(topics.get(1));
        task3.setModule(task3.getTopic().getModule());
        task3.setCreateTime(new Date());
        task3.setModifiedTime(new Date());
        task3.setExecutionTime(30);
        tasks.add(taskRepository.save(task3));

        return tasks;
    }

    private List<CategoryModel> initCategories(List<TaskModel> tasks) {
        List<CategoryModel> categories = new ArrayList<>();

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setUid("1");
        categoryModel.setDisplayName("Functional");
        categoryModel.setCreateTime(new Date());
        categoryModel.setModifiedTime(new Date());
        categoryModel.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel));

        CategoryModel categoryModel1 = new CategoryModel();
        categoryModel1.setUid("2");
        categoryModel1.setDisplayName("UI Requirement");
        categoryModel1.setCreateTime(new Date());
        categoryModel1.setModifiedTime(new Date());
        categoryModel1.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel1));

        CategoryModel categoryModel2 = new CategoryModel();
        categoryModel2.setUid("3");
        categoryModel2.setDisplayName("Performance");
        categoryModel2.setCreateTime(new Date());
        categoryModel2.setModifiedTime(new Date());
        categoryModel2.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel2));

        CategoryModel categoryModel3 = new CategoryModel();
        categoryModel3.setUid("4");
        categoryModel3.setDisplayName("A Valid Requirement");
        categoryModel3.setCreateTime(new Date());
        categoryModel3.setModifiedTime(new Date());
        categoryModel3.setTask(tasks.get(1));
        categories.add(categoryRepository.save(categoryModel3));

        CategoryModel categoryModel4 = new CategoryModel();
        categoryModel4.setUid("5");
        categoryModel4.setDisplayName("An Invalid Requirement");
        categoryModel4.setCreateTime(new Date());
        categoryModel4.setModifiedTime(new Date());
        categoryModel4.setTask(tasks.get(1));
        categories.add(categoryRepository.save(categoryModel4));

        CategoryModel categoryModel5 = new CategoryModel();
        categoryModel5.setUid("6");
        categoryModel5.setDisplayName("High Priority");
        categoryModel5.setCreateTime(new Date());
        categoryModel5.setModifiedTime(new Date());
        categoryModel5.setTask(tasks.get(1));
        categories.add(categoryRepository.save(categoryModel5));

        return categories;
    }

    private List<ModuleModel> initModules() {
        List<ModuleModel> moduleModels = new ArrayList<>();

        ModuleModel moduleModel = new ModuleModel();
        moduleModel.setId(1L);
        moduleModel.setUid("4341231");
        moduleModel.setCreateTime(new Date());
        moduleModel.setModifiedTime(new Date());
        moduleModel.setDisplayName("Requirement Analysis");
        moduleModels.add(moduleRepository.save(moduleModel));

        ModuleModel moduleModel2 = new ModuleModel();
        moduleModel2.setId(2L);
        moduleModel2.setUid("4341232");
        moduleModel2.setCreateTime(new Date());
        moduleModel2.setModifiedTime(new Date());
        moduleModel2.setDisplayName("Design");
        moduleModels.add(moduleRepository.save(moduleModel2));

        ModuleModel moduleModel3 = new ModuleModel();
        moduleModel3.setId(3L);
        moduleModel3.setUid("4341233");
        moduleModel3.setCreateTime(new Date());
        moduleModel3.setModifiedTime(new Date());
        moduleModel3.setDisplayName("Development");
        moduleModels.add(moduleRepository.save(moduleModel3));

        ModuleModel moduleModel4 = new ModuleModel();
        moduleModel4.setId(4L);
        moduleModel4.setUid("4341234");
        moduleModel4.setCreateTime(new Date());
        moduleModel4.setModifiedTime(new Date());
        moduleModel4.setDisplayName("Testing");
        moduleModels.add(moduleRepository.save(moduleModel4));

        ModuleModel moduleModel5 = new ModuleModel();
        moduleModel5.setId(5L);
        moduleModel5.setUid("4341235");
        moduleModel5.setCreateTime(new Date());
        moduleModel5.setModifiedTime(new Date());
        moduleModel5.setDisplayName("Deployment");
        moduleModels.add(moduleRepository.save(moduleModel5));
        return moduleModels;
    }

    private List<TopicModel> initTopics(List<ModuleModel> modules) {
        List<TopicModel> topicModels = new ArrayList<>();

        TopicModel topicModel = new TopicModel();
        topicModel.setId(1L);
        topicModel.setUid("315121");
        topicModel.setDisplayName("Types of requirements");
        topicModel.setModule(modules.get(0));
        topicModel.setCreateTime(new Date());
        topicModel.setModifiedTime(new Date());
        topicModels.add(topicRepository.save(topicModel));

        TopicModel topicModel2 = new TopicModel();
        topicModel2.setId(2L);
        topicModel2.setUid("315122");
        topicModel2.setDisplayName("Requirement priority");
        topicModel2.setModule(modules.get(0));
        topicModel2.setCreateTime(new Date());
        topicModel2.setModifiedTime(new Date());
        topicModels.add(topicRepository.save(topicModel2));


        TopicModel topicModel3 = new TopicModel();
        topicModel3.setId(3L);
        topicModel3.setUid("315123");
        topicModel3.setDisplayName("Topic 3");
        topicModel3.setModule(modules.get(0));
        topicModel3.setCreateTime(new Date());
        topicModel3.setModifiedTime(new Date());
        topicModels.add(topicRepository.save(topicModel3));

        return topicModels;
    }

    private List<UserModel> initUsers() {
        List<UserModel> users = new ArrayList<>();

        UserModel userModel = new UserModel();
        userModel.setId(1L);
        userModel.setUid("1321321");
        userModel.setRole(Role.ADMIN);
        userModel.setAge(25);
        userModel.setName("Marko");
        userModel.setSurname("Popyk");
        userModel.setEmail("admin@gmail.com");
        userModel.setBirthDate(new Date());
        userModel.setLogin("admin");
        userModel.setPassword(encryptionHelper.encrypt("admin"));
        userModel.setCreateTime(new Date());
        userModel.setModifiedTime(new Date());
        users.add(userRepository.save(userModel));

        UserModel userModel2 = new UserModel();
        userModel2.setId(2L);
        userModel2.setUid("1321322");
        userModel2.setRole(Role.USER);
        userModel2.setAge(21);
        userModel2.setName("Truba");
        userModel2.setSurname("Ostap");
        userModel2.setEmail("ostap@gmail.com");
        userModel2.setBirthDate(new Date());
        userModel2.setLogin("ostap");
        userModel2.setPassword(encryptionHelper.encrypt("truba"));
        userModel2.setCreateTime(new Date());
        userModel2.setModifiedTime(new Date());
        users.add(userRepository.save(userModel2));

        return users;
    }
}
