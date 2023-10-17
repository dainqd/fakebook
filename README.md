READ

** LOGIN INFORMATION (username/password):

<p>User: user / 123456 </p>
<p>Admin: admin / 123456 </p>

<p>**SETTING</p>

You need to run it locally by following these steps:

1. Download and install Xampp (choose the version with PHP 8.1): https://www.apachefriends.org/download.html (for MacOS,
   your can use MAMP to replace)
2. Open Xampp and start Apache & MySQL
3. Follow project: https://github.com/dainqd/fakebook
4. Open terminal, run command: <span> git clone https://github.com/dainqd/fakebook </span> to download the source code.
5. Open project with your IDE(such as IntelliJ IDEA or more tool other...)
6. Open terminal, run command: <span style="color:green; font-size: 15px"> cp
   src/main/resources/.application.example.properties src/main/resources/application.properties </span>
7. You must make a connection to the database and set up more add variables and keys for the project
   environment: <span style="color:red">application.properties</span>
8. Run command:
    <ol>
         <li> cd ImageApp</li>
         <li> composer install</li>
         <li> cp .env.example .env (Notes: Setup config database in file: <span style="color: red">.env</span>)</li>
         <li> php artisan migrate</li>
         <li> php artisan key:generate</li>
         <li> php artisan serve</li>
    </ol>
   <p>The later, you open new terminal tab, and run command: </p>
   <ol>
         <li> cd FrontEnd</li>
         <li> npm install</li>
         <li> npm start</li>
   </ol>
9. Follow link to test project:
   <ol>
         <li> Client: <a href="http://localhost:8888/">HomePage</a></li>
         <li> Admin:  <a href="http://localhost:3000/">Admin</a></li>
   </ol>
10. Notes
    <p>Backend: Java - SpringBoot</p>
    <p>Serve Upload Image: PHP - Laravel</p>
    <p>Client: Thymeleaf, JavaScript, jQuery, HTML 5, CSS, websocket,...</p>
    <p>Admin: ReactJS</p>