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

        VariantModel variantModel111 = new VariantModel();
        variantModel111.setUid("325234");
        variantModel111.setDisplayName("Variant111");
        variantModel111.setCategory(categories.get(0));
        variantModel111.setTask(variantModel111.getCategory().getTask());
        variantModel111.setTooltip("Tooltip for 111");
        variantModel111.setCreateTime(new Date());
        variantModel111.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel111));

        VariantModel variantModel112 = new VariantModel();
        variantModel112.setUid("325235");
        variantModel112.setDisplayName("Variant112");
        variantModel112.setCategory(categories.get(0));
        variantModel112.setTask(variantModel111.getCategory().getTask());
        variantModel112.setTooltip("Tooltop for 112");
        variantModel112.setCreateTime(new Date());
        variantModel112.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel112));

        VariantModel variantModel121 = new VariantModel();
        variantModel121.setUid("325236");
        variantModel121.setDisplayName("Variant121");
        variantModel121.setCategory(categories.get(1));
        variantModel121.setTask(variantModel121.getCategory().getTask());
        variantModel121.setTooltip("Tooltop for 121");
        variantModel121.setCreateTime(new Date());
        variantModel121.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel121));

        VariantModel variantModel122 = new VariantModel();
        variantModel122.setUid("325237");
        variantModel122.setDisplayName("Variant122");
        variantModel122.setCategory(categories.get(1));
        variantModel122.setTask(variantModel122.getCategory().getTask());
        variantModel122.setTooltip("Tooltop for 122");
        variantModel122.setCreateTime(new Date());
        variantModel122.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel122));

        VariantModel variantModel131 = new VariantModel();
        variantModel131.setUid("325238");
        variantModel131.setDisplayName("Variant131");
        variantModel131.setCategory(categories.get(2));
        variantModel131.setTask(variantModel131.getCategory().getTask());
        variantModel131.setTooltip("Tooltop for 131");
        variantModel131.setCreateTime(new Date());
        variantModel131.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel131));


        VariantModel variantModel132 = new VariantModel();
        variantModel132.setUid("325239");
        variantModel132.setDisplayName("Variant132");
        variantModel132.setCategory(categories.get(2));
        variantModel132.setTask(variantModel132.getCategory().getTask());
        variantModel132.setTooltip("Tooltop for 132");
        variantModel132.setCreateTime(new Date());
        variantModel132.setModifiedTime(new Date());
        variantModels.add(variantRepository.save(variantModel132));

        return variantModels;
    }

    private List<TaskModel> initTasks(List<TopicModel> topics) {
        List<TaskModel> tasks = new ArrayList<>();

        TaskModel task1 = new TaskModel();
        task1.setUid("343242");
        task1.setDisplayName("Task 1");
        task1.setLevel(Level.EASY);
        task1.setTopic(topics.get(0));
        task1.setModule(task1.getTopic().getModule());
        task1.setCreateTime(new Date());
        task1.setModifiedTime(new Date());
        tasks.add(taskRepository.save(task1));

        TaskModel task2 = new TaskModel();
        task2.setUid("343243");
        task2.setDisplayName("Task 2");
        task2.setLevel(Level.NORMAL);
        task2.setTopic(topics.get(0));
        task2.setModule(task2.getTopic().getModule());
        task2.setCreateTime(new Date());
        task2.setModifiedTime(new Date());
        tasks.add(taskRepository.save(task2));


        TaskModel task3 = new TaskModel();
        task3.setUid("343244");
        task3.setDisplayName("Task 3");
        task3.setLevel(Level.HARD);
        task3.setTopic(topics.get(0));
        task3.setModule(task3.getTopic().getModule());
        task3.setCreateTime(new Date());
        task3.setModifiedTime(new Date());
        tasks.add(taskRepository.save(task3));

        return tasks;
    }

    private List<CategoryModel> initCategories(List<TaskModel> tasks) {
        List<CategoryModel> categories = new ArrayList<>();

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setUid("432231");
        categoryModel.setDisplayName("Category 1");
        categoryModel.setCreateTime(new Date());
        categoryModel.setModifiedTime(new Date());
        categoryModel.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel));

        CategoryModel categoryModel1 = new CategoryModel();
        categoryModel1.setUid("432232");
        categoryModel1.setDisplayName("Category 2");
        categoryModel1.setCreateTime(new Date());
        categoryModel1.setModifiedTime(new Date());
        categoryModel1.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel1));

        CategoryModel categoryModel2 = new CategoryModel();
        categoryModel2.setUid("432233");
        categoryModel2.setDisplayName("Category 3");
        categoryModel2.setCreateTime(new Date());
        categoryModel2.setModifiedTime(new Date());
        categoryModel2.setTask(tasks.get(0));
        categories.add(categoryRepository.save(categoryModel2));

        CategoryModel categoryModel3 = new CategoryModel();
        categoryModel3.setUid("432235");
        categoryModel3.setDisplayName("Category 2.1");
        categoryModel3.setCreateTime(new Date());
        categoryModel3.setModifiedTime(new Date());
        categoryModel3.setTask(tasks.get(1));
        categories.add(categoryRepository.save(categoryModel3));

        CategoryModel categoryModel4 = new CategoryModel();
        categoryModel4.setUid("432236");
        categoryModel4.setDisplayName("Category 2.2");
        categoryModel4.setCreateTime(new Date());
        categoryModel4.setModifiedTime(new Date());
        categoryModel4.setTask(tasks.get(1));
        categories.add(categoryRepository.save(categoryModel4));

        CategoryModel categoryModel5 = new CategoryModel();
        categoryModel5.setUid("432237");
        categoryModel5.setDisplayName("Category 2.3");
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
        topicModel.setDisplayName("Topic 1");
        topicModel.setModule(modules.get(0));
        topicModel.setCreateTime(new Date());
        topicModel.setModifiedTime(new Date());
        topicModels.add(topicRepository.save(topicModel));

        TopicModel topicModel2 = new TopicModel();
        topicModel2.setId(2L);
        topicModel2.setUid("315122");
        topicModel2.setDisplayName("Topic 2");
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
