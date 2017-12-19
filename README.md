# Booking Manager

Team project for PA165 course at Masaryk University in Brno, Faculty of Informatics.

## Official project description
Let us imagine a Booking Manager for hotels: every customer can choose desired hotel and a particular room. There is also an information about accommodation price. It should be possible to browse hotels and available rooms. System Administrator should be also able to find customers who have some room reserved in a certain time range.

## Web application
Application runs natively at `http://localhost:8080/pa165`, web UI is created in React. For the proper running of the app, it is necessary to have Node.js installed in your computer and run these commands from the parent folder of the project:

```
mvn clean install
cd frontend
npm install
npm run dev
mvn tomcat7:run
```

For more information on [REST api](https://github.com/xsustek/bookingmanager/wiki/REST-Api) see projects wiki pages.

## Team members
* Milan Šůstek
* Tomáš Kopecký
* Peter Neupauer
* Viktória Tibenská

For more information about this project, see its [Wiki pages](https://github.com/xsustek/bookingmanager/wiki).
