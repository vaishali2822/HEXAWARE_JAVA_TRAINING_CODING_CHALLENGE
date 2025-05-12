-- 1. create database
create database PetPals;
use PetPals;

-- 2 create table
create table pets
(
 petID int primary key auto_increment,
 Name varchar(50) not null,
 Age int,
 Breed varchar(50) not null,
 Type varchar(100) not null,
 AvailableForAdoption boolean 
 
);

create table Shelters
(
 ShelterID int primary key auto_increment,
 Name varchar(100) not null,
 Location varchar(250) not null
);


create table Donations
(
 DonationID int primary key auto_increment,
 DonorName varchar(100) not null,
 DonationType varchar(100) not null,
 DonationAmount decimal(10,2) not null ,
 DonationItem varchar(250) not null,
 DonationDate datetime 
);


create table AdoptionEvents
(
 EventID int primary key auto_increment,
 EventName varchar(100) not null ,
 EventDate date,
 Location varchar(250) not null
);

create table Participants 
(
 ParticipantsID int primary key auto_increment,
 ParticipantName varchar(100) not null,
 ParticipantsType varchar(200) not null,
 EventID int ,
 constraint fk foreign key (EventID) references AdoptionEvents(EventID) on delete cascade
);

INSERT INTO pets (Name, Age, Breed, Type, AvailableForAdoption) VALUES
('Buddy', 3, 'Golden Retriever', 'Dog', true),
('Mittens', 2, 'Siamese', 'Cat', true),
('Charlie', 1, 'Labrador', 'Dog', false),
('Bella', 4, 'Persian', 'Cat', true),
('Nibbles', 1, 'Netherland Dwarf', 'Rabbit', true);

select * from pets;

INSERT INTO Shelters (Name, Location) VALUES
('Happy Tails Shelter', '123 Paw Street, Los Angeles, CA'),
('FurEver Homes', '456 Woof Road, Dallas, TX'),
('Paw Haven', '789 Bark Avenue, Chicago, IL'),
('Safe Haven Shelter', '101 Kitty Blvd, Miami, FL'),
('Rescue Retreat', '202 Tail Lane, Seattle, WA');
select * from Shelters ;

INSERT INTO Donations (DonorName, DonationType, DonationAmount, DonationItem, DonationDate) VALUES
('vaishali', 'cash', 1000.00, 'N/A', '2024-12-01 10:30:00'),
('ashly p joy', 'Item', 10000.00, 'Dog food - 20kg', '2024-12-03 15:45:00'),
('viswa', 'cash', 2250.00, 'N/A', '2025-01-10 12:00:00'),
('badri', 'Item', 222.00, 'Cat toys and beds', '2025-02-14 09:15:00'),
('sharoon', 'cash', 5220.00, 'N/A', '2025-03-05 14:20:00');

select *from Donations ;

INSERT INTO AdoptionEvents (EventName, EventDate, Location) VALUES
('Paws for Love', '2025-04-01', 'Community Park, chennai'),
('pets mela', '2025-04-15', 'Downtown Plaza, avadi'),
('Adopt-A-Pet Day', '2025-04-22', 'City Shelter Grounds, anna nagar'),
('Pet Paradize', '2025-05-01', 'Main Street,madurai'),
('tamil nadu Adoption Fair', '2025-05-10', 'Green Park,padi');

select * from AdoptionEvents;

INSERT INTO Participants (ParticipantName, ParticipantsType, EventID) VALUES
('Sarah', 'Volunteer', 1),
('kaviya', 'Shelter', 2),
('suriya', 'Shelter', 3),
('priya', 'Volunteer', 4),
('yuvashri', 'Shelter', 5);

select * from Participants;

-- 4
create database  if not Exists Petpals;

create table if not Exists Participants 
(
 ParticipantsID int primary key auto_increment,
 ParticipantName varchar(100) not null,
 ParticipantsType varchar(200) not null,
 EventID int ,
 constraint fk foreign key (EventID) references AdoptionEvents(EventID) on delete cascade
);

-- 5 
select Name, Age, Breed, Type
from pets
where AvailableForAdoption = 1;

-- 6
select p.ParticipantName, p.ParticipantsType
from Participants p
join AdoptionEvents a ON p.EventID = a.EventID
where a.EventName = 'Paws for Love';

-- 7
update Shelters
set name = 'heaven Shelter',
    Location = '564 red riding wood'
where ShelterID = 4;

select * from Shelters; 


-- 8
ALTER TABLE donations ADD ShelterID INT;
ALTER TABLE donations
ADD CONSTRAINT fk_donations_shelter
FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID);

UPDATE donations SET ShelterID = 1 WHERE DonationID = 1;
UPDATE donations SET ShelterID = 2 WHERE DonationID = 2;
UPDATE donations SET ShelterID = 1 WHERE DonationID = 3;
UPDATE donations SET ShelterID = 3 WHERE DonationID = 4;
UPDATE donations SET ShelterID = 4 WHERE DonationID = 5;
select * from adoption ;

select s.name as ShelterName , sum(donationamount) as Amount
from Shelters s
inner join donations d 
on s.ShelterID = d.ShelterID
group by s.name
order by Amount desc;

select * from donations ;


-- 9
Select * from Pets 
where AvailableForAdoption=1;


-- 10
select date_format(min(Donationdate) ,'%M %Y') as Month_Date,
sum(DonationAmount) as TotalDonationAmount
from donations
group by Month(DonationDate) 
order by Month(DonationDate) ;

-- 11
select distinct (breed) , age from pets 
where age between 1 and 3 or age >5;

-- 12
ALTER TABLE pets
ADD COLUMN ShelterID INT,
ADD CONSTRAINT fk_shelter
    FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID)
    ON DELETE cascade;
    
UPDATE pets SET ShelterID = 1 WHERE petID = 1;
UPDATE pets SET ShelterID = 2 WHERE petID = 2;
UPDATE pets SET ShelterID = 1 WHERE petID = 3;
UPDATE pets SET ShelterID = 3 WHERE petID = 4;
UPDATE pets SET ShelterID = 4 WHERE petID = 5;

SELECT p.Name AS PetName, s.Name AS ShelterName
FROM pets p
JOIN Shelters s ON p.ShelterID = s.ShelterID
WHERE p.AvailableForAdoption =  1;


-- 13
select count(*) as TotalParticipants
from participants p
join AdoptionEvents a
on  p.EventID = a.EventID
where a.location like '%chennai%';


-- 14
SELECT DISTINCT Breed
FROM pets
WHERE petID IN (
    SELECT petID
    FROM pets
    WHERE Age BETWEEN 1 AND 5
);


-- 15
SELECT *
FROM pets
WHERE petID NOT IN (
    SELECT PetID FROM pets
    where AvailableForAdoption = 0
);


-- 16
CREATE TABLE if not exists Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    UserName VARCHAR(100) NOT NULL,
    ContactInfo VARCHAR(255)
);


CREATE TABLE if not Exists Adoption (
    AdoptionID INT PRIMARY KEY AUTO_INCREMENT,
    PetID INT,
    UserID INT,
    AdoptionDate DATE,
    FOREIGN KEY (PetID) REFERENCES pets(petID),
    FOREIGN KEY (UserID) REFERENCES Users(UserID)
);


INSERT INTO Users (UserName, ContactInfo) VALUES
('Ananya', 'ananya@example.com'),
('Ravi Kumar', 'ravi.kumar@example.com'),
('Sneha', 'sneha@example.com');


INSERT INTO Adoption (PetID, UserID, AdoptionDate) VALUES
(1, 1, '2025-03-15'),  
(3, 2, '2025-04-01'),  
(4, 3, '2025-04-12');

SELECT p.Name AS PetName,u.UserName AS AdopterName
FROM Adoption a
JOIN pets p ON a.PetID = p.petID
JOIN Users u ON a.UserID = u.UserID;


-- 17
SELECT s.Name AS ShelterName, COUNT(p.petID) AS AvailablePets
FROM Shelters s
LEFT JOIN pets p ON s.ShelterID = p.ShelterID 
where p.AvailableForAdoption = TRUE
GROUP BY s.ShelterID, s.Name;


-- 18
SELECT p1.Name AS Pet1, p2.Name AS Pet2, p1.Breed, s.Name AS ShelterName
FROM pets p1
JOIN pets p2 ON p1.Breed = p2.Breed AND p1.petID < p2.petID AND p1.ShelterID = p2.ShelterID
JOIN Shelters s ON p1.ShelterID = s.ShelterID;


-- 19
SELECT s.Name AS ShelterName, e.EventName AS EventName
FROM Shelters s
CROSS JOIN AdoptionEvents e;

  
-- 20
ALTER TABLE Adoption ADD ShelterID INT;
ALTER TABLE Adoption
ADD CONSTRAINT fk_adoption_shelter
FOREIGN KEY (ShelterID) REFERENCES Shelters(ShelterID);

select * from adoption ;


UPDATE adoption SET ShelterID = 1 WHERE petID = 1;
UPDATE adoption SET ShelterID = 2 WHERE petID = 2;
UPDATE adoption SET ShelterID = 1 WHERE petID = 4;

SELECT s.Name AS ShelterName, COUNT(*) AS AdoptedPetsCount
FROM Adoption a
JOIN Shelters s ON a.ShelterID = s.ShelterID
GROUP BY s.ShelterID
ORDER BY AdoptedPetsCount DESC
LIMIT 1;
