# hotelReservationSystem
This is a demo application for a hotel reservation system. <br>

Live version: 
<a href="https://hotel-reservation-demo.herokuapp.com/">Click!</a>

<p>Apart from live view there are prepared some API endpoints:
<ul>
    <li>GET /guests/all - returns all guests</li>
    <li>GET /guests/ - with request param "uuid" - returns guest for given uuid</li>
    <li>GET /guests/ - with request param "phone" - returns guest for given phone</li>
    <li>GET /guests/ - with request param "email" - returns guest for given email</li>
    <li>GET /guests/ - with request param "firstName" and "secondName" - returns guest for given name</li>
    <li>DELETE /guests/ - with request param "uuid" - deletes guest for given uuid</li>   
    <li>POST /guests/add - with request JSON body "firstName, secondName, email, phone" - creates new guest</li>
    <li>Patch /guests - with request JSON body (guest) - actualize guest data</li>
    <br>
    <li>GET /rooms/all - returns all rooms</li>
    <li>DELETE /rooms/ - with request param "number" - deletes room for given number</li>
    <li>POST /rooms/add - with request JSON body "number, floor, beds" - creates new room</li>
    <li>PATCH /rooms/ - with request JSON body (room) - actualize room data</li>
    <br>
    <li>GET /reservations/all - returns all reservations</li>
    <li>GET /reservations/guest/{guestUuid} - returns reservations for given guest</li>
    <li>GET /reservations/room/{roomNumber} - returns reservations for given room</li>
    <li>DELETE /reservations/ -with request param "uuid" - deletes reservation with given uuid</li>
    <li>POST /reservations/add -with request params "guestUuid", "roomNumber", "start", "end" - adds new reservation</li>
</ul>
</p>

Technologies and tools used to make the application:
<ol>
    <li>Core</li>
            <ul>
            <li>Java</li>
            <li>Spring Boot</li>
            </ul>
    <li>Data</li>
            <ul>
            <li>H2 database</li>
            <li>Flyway</li>
            </ul>
    <li>View</li>
            <ul>
            <li>Thymeleaf</li>
            <li>HTML</li>
            <li>CSS</li>
            </ul>
    <li>Test</li>
            <ul>
            <li>JUnit</li>
            <li>Mockito</li>
            <li>Postman</li>
            </ul>
</ol>


<h2>What can you do:</h2>
<h3>1. Create account and edit it</h3>
    <img src="https://user-images.githubusercontent.com/85845022/122363297-1e296380-cf59-11eb-86b2-f8f36169c804.PNG" >
    <img src="https://user-images.githubusercontent.com/85845022/122363302-1ec1fa00-cf59-11eb-8761-9e92de525c29.PNG" >
    <br>
<h3>2. See your current and past reservations</h3>
    <img src="https://user-images.githubusercontent.com/85845022/122363288-1c5fa000-cf59-11eb-9576-1e73a7de1692.PNG" >
<h3>3. Create reservation and choose the room</h3>
    <img src="https://user-images.githubusercontent.com/85845022/122363305-1f5a9080-cf59-11eb-9d7c-0b6eb3d8b712.PNG" >
    <br>
    <img src="https://user-images.githubusercontent.com/85845022/122363309-1ff32700-cf59-11eb-9480-2da6df022c30.PNG" >
<h3>4. See and confirm reservation summary</h3>
    <img src="https://user-images.githubusercontent.com/85845022/122363316-208bbd80-cf59-11eb-933d-aa4b6b8022b4.PNG" >
