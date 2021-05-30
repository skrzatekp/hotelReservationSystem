CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(3) UNIQUE NOT NULL,
    floor INT NOT NULL,
    beds INT NOT NULL
);

CREATE TABLE guests (
    guest_id INT PRIMARY KEY AUTO_INCREMENT,
    uuid VARCHAR(50) NOT NULL,
    firstName VARCHAR(15) NOT NULL,
    secondName VARCHAR(25) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    uuid VARCHAR(50) NOT NULL,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    start DATE NOT NULL,
    end DATE NOT NULL,
    foreign key (guest_id) references guests(guest_id),
    foreign key (room_id ) references rooms(room_id)
 );
