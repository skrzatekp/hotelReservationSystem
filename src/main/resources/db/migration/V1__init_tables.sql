CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    number VARCHAR(3) UNIQUE NOT NULL,
    floor INT NOT NULL,
    beds INT NOT NULL,
    cost DECIMAL NOT NULL
);

CREATE TABLE guests (
    guest_id INT PRIMARY KEY AUTO_INCREMENT,
    uuid VARCHAR(50) NOT NULL,
    first_name VARCHAR(15) NOT NULL,
    second_name VARCHAR(25) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    uuid VARCHAR(50) NOT NULL,
    guest_id INT,
    room_id INT NOT NULL,
    start DATE NOT NULL,
    end DATE NOT NULL,
    foreign key (guest_id) references guests(guest_id) on delete set null,
    foreign key (room_id ) references rooms(room_id)
 );
