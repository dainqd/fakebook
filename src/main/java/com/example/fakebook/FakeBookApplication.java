package com.example.fakebook;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Blog;
import com.example.fakebook.entities.Comments;
import com.example.fakebook.entities.Roles;
import com.example.fakebook.repositories.AccountRepository;
import com.example.fakebook.repositories.BlogRepository;
import com.example.fakebook.repositories.CommentRepository;
import com.example.fakebook.repositories.RoleRepository;
import com.example.fakebook.service.RoleService;
import com.example.fakebook.service.SeedData;
import com.example.fakebook.utils.Enums;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.*;

@SpringBootApplication
public class FakeBookApplication implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    CommentRepository commentRepository;

    private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(FakeBookApplication.class, args);
    }

    @Value("${i18n.localechange.interceptor.default}")
    String localeChangeInterceptorParaName;

    @Value("${i18n.resourcebundle.message.source.default}")
    String resourceBundleMessageSourceBase;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(localeChangeInterceptorParaName);
        return lci;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(resourceBundleMessageSourceBase);
        return messageSource;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ApplicationContext ctx) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(ctx);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void run(String... args) throws Exception {
//        Roles roleUser = new Roles(Enums.Roles.USER);
//        Roles roleAdmin = new Roles(Enums.Roles.ADMIN);
//        roleUser = roleRepository.save(roleUser);
//        roleAdmin = roleRepository.save(roleAdmin);
//
//        Accounts accountAdmin = new Accounts("https://t4.ftcdn.net/jpg/05/49/98/39/360_F_549983970_bRCkYfk0P6PP5fKbMhZMIb07mCJ6esXL.jpg", "admin", "admin@gmail.com", passwordEncoder.encode("123456"), Enums.AccountStatus.ACTIVE);
//        Set<Roles> roleAdmins = new HashSet<>();
//        roleAdmins.add(roleUser);
//        roleAdmins.add(roleAdmin);
//        accountAdmin.setRoles(roleAdmins);
//        accountAdmin.setVerified(true);
//        accountAdmin = accountRepository.save(accountAdmin);
//        Accounts accountUser = new Accounts("https://s3.nucuoimekong.com/ncmk/wp-content/uploads/buc-anh-dep-can-bang-sang-tot-1.jpg", "user", "user@gmail.com", passwordEncoder.encode("123456"), Enums.AccountStatus.ACTIVE);
//        Set<Roles> roleUsers = new HashSet<>();
//        roleUsers.add(roleUser);
//        accountUser.setRoles(roleUsers);
//        accountUser.setVerified(true);
//        accountUser = accountRepository.save(accountUser);
//
//        Blog blogAdmin = new Blog(accountAdmin, "https://www.elle.vn/wp-content/uploads/2017/07/25/hinh-anh-dep-1.jpg", "Hello world", 1, 2, 3, 5, Enums.BlogStatus.ACTIVE);
//        blogAdmin = blogRepository.save(blogAdmin);
//        Blog blogUser = new Blog(accountUser, "https://nld.mediacdn.vn/thumb_w/540/2020/5/29/doi-hoa-tim-8-15907313395592061395682.png", "Xin chao", 5, 2, 3, 1, Enums.BlogStatus.ACTIVE);
//        blogUser = blogRepository.save(blogUser);
//
//        Comments comments = new Comments(accountAdmin, blogAdmin, "Hay qua", 1, null, Enums.CommentStatus.ACTIVE);
//        comments = commentRepository.save(comments);
//        Comments commentChild = new Comments(accountUser, null, "Comment cap 2", 1, comments, Enums.CommentStatus.ACTIVE);
//        commentChild = commentRepository.save(commentChild);
//        Comments commentUser = new Comments(accountUser, blogUser, "Comment User", 1, null, Enums.CommentStatus.ACTIVE);
//        commentUser = commentRepository.save(commentUser);
    }
}
