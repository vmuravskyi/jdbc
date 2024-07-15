-- Create the flight_repository database
CREATE DATABASE flight_repository;

-- Switch to the flight_repository database
\c flight_repository

-- Create the airport table
CREATE TABLE airport (
    code CHAR(3) PRIMARY KEY,
    country VARCHAR(256) NOT NULL,
    city VARCHAR(128) NOT NULL
);

-- Create the aircraft table
CREATE TABLE aircraft (
    id SERIAL PRIMARY KEY,
    model VARCHAR(128) UNIQUE NOT NULL
);

-- Create the seat table
CREATE TABLE seat (
    aircraft_id INT REFERENCES aircraft (id),
    seat_no VARCHAR(4) NOT NULL,
    PRIMARY KEY (aircraft_id, seat_no)
);

-- Create the flight table
CREATE TABLE flight (
    id BIGSERIAL PRIMARY KEY,
    flight_no VARCHAR(16) NOT NULL,
    departure_date TIMESTAMP NOT NULL,
    departure_airport_code CHAR(3) REFERENCES airport (code) NOT NULL,
    arrival_date TIMESTAMP NOT NULL,
    arrival_airport_code CHAR(3) REFERENCES airport (code) NOT NULL,
    aircraft_id INT REFERENCES aircraft (id) NOT NULL,
    status VARCHAR(32) NOT NULL
);

-- Create the ticket table
CREATE TABLE ticket (
    id BIGSERIAL PRIMARY KEY,
    passenger_no VARCHAR(32) NOT NULL,
    passenger_name VARCHAR(128) NOT NULL,
    flight_id BIGINT REFERENCES flight (id) NOT NULL,
    seat_no VARCHAR(4) NOT NULL,
    cost NUMERIC(8, 2) NOT NULL,
    UNIQUE (flight_id, seat_no)
);

-- Insert data into the airport table
INSERT INTO airport (code, country, city) VALUES
('JFK', 'United States', 'New York'),
('LAX', 'United States', 'Los Angeles'),
('ORD', 'United States', 'Chicago'),
('DFW', 'United States', 'Dallas');

-- Insert data into the aircraft table
INSERT INTO aircraft (model) VALUES
('Boeing 777-300'),
('Boeing 737-300'),
('Airbus A320-200'),
('Embraer E190');

-- Insert data into the seat table
INSERT INTO seat (aircraft_id, seat_no)
SELECT id, s.column1
FROM aircraft
CROSS JOIN (VALUES ('A1'), ('A2'), ('B1'), ('B2'), ('C1'), ('C2'), ('D1'), ('D2')) s(column1)
ORDER BY 1;

-- Insert data into the flight table
INSERT INTO flight (flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status) VALUES
('AA1002', '2023-06-14T14:30', 'JFK', '2023-06-14T18:07', 'LAX', 1, 'ARRIVED'),
('AA1003', '2023-06-16T09:15', 'LAX', '2023-06-16T13:00', 'JFK', 1, 'ARRIVED'),
('UA2001', '2023-07-28T23:25', 'ORD', '2023-07-29T02:43', 'DFW', 2, 'ARRIVED'),
('UA2002', '2023-08-01T11:00', 'DFW', '2023-08-01T14:15', 'ORD', 2, 'DEPARTED'),
('DL3003', '2023-05-03T13:10', 'JFK', '2023-05-03T18:38', 'LAX', 3, 'ARRIVED'),
('DL3004', '2023-05-10T07:15', 'LAX', '2023-05-10T12:44', 'JFK', 3, 'CANCELLED'),
('AA4007', '2023-09-09T18:00', 'ORD', '2023-09-09T19:15', 'DFW', 4, 'SCHEDULED'),
('AA4008', '2023-09-19T08:55', 'DFW', '2023-09-19T10:05', 'ORD', 4, 'SCHEDULED'),
('UA5001', '2023-12-18T03:35', 'JFK', '2023-12-18T06:46', 'LAX', 2, 'ARRIVED');

-- Insert data into the ticket table
INSERT INTO ticket (passenger_no, passenger_name, flight_id, seat_no, cost) VALUES
('112233', 'John Doe', 1, 'A1', 200.00),
('23234A', 'Jane Smith', 1, 'B1', 180.00),
('SS988D', 'Michael Johnson', 1, 'B2', 175.00),
('QYASDE', 'Emily Davis', 1, 'C2', 175.00),
('POQ234', 'James Brown', 1, 'D1', 160.00),
('898123', 'Patricia Wilson', 1, 'A2', 198.00),
('555321', 'Linda Martinez', 2, 'A1', 250.00),
('QO23OO', 'Barbara Taylor', 2, 'B2', 225.00),
('9883IO', 'Elizabeth Anderson', 2, 'C1', 217.00),
('123UI2', 'Jennifer Thomas', 2, 'C2', 227.00),
('SS988D', 'Michael Johnson', 2, 'D2', 277.00),
('EE2344', 'William Jackson', 3, 'A1', 300.00),
('AS23PP', 'David White', 3, 'A2', 285.00),
('322349', 'Richard Harris', 3, 'B1', 99.00),
('DL123S', 'Charles Clark', 3, 'B2', 199.00),
('MVM111', 'Christopher Lewis', 3, 'C1', 299.00),
('ZZZ111', 'Daniel Robinson', 3, 'C2', 230.00),
('234444', 'Matthew Walker', 3, 'D1', 180.00),
('LLLL12', 'Anthony Hall', 3, 'D2', 224.00),
('RT34TR', 'Mark Young', 4, 'A1', 129.00),
('999666', 'Joshua King', 4, 'A2', 152.00),
('234444', 'Matthew Walker', 4, 'B1', 140.00),
('LLLL12', 'Anthony Hall', 4, 'B2', 140.00),
('AAAA12', 'Jason Wright', 4, 'D2', 109.00), -- Fixed duplicate
('112233', 'John Doe', 5, 'C2', 170.00),
('NMNBV2', 'Jessica Lopez', 5, 'C1', 185.00),
('DSA586', 'Sarah Gonzalez', 5, 'A1', 204.00),
('DSA583', 'Andrew Perez', 5, 'B1', 189.00),
('DSA581', 'Ryan Davis', 6, 'A1', 204.00),
('EE2344', 'William Jackson', 6, 'A2', 214.00),
('AS23PP', 'David White', 6, 'B2', 176.00),
('112233', 'John Doe', 6, 'B1', 135.00),
('309623', 'Elizabeth Hernandez', 6, 'C1', 155.00),
('319623', 'Joseph Allen', 6, 'D1', 125.00),
('322349', 'Richard Harris', 7, 'A1', 69.00),
('DIOPSL', 'Kenneth Wright', 7, 'A2', 58.00),
('DIOPS1', 'Steven Scott', 7, 'D1', 65.00),
('DIOPS2', 'Brian Green', 7, 'D2', 65.00),
('1IOPS2', 'George Adams', 7, 'C2', 73.00),
('999666', 'Joshua King', 7, 'B1', 66.00),
('23234A', 'Jane Smith', 7, 'C1', 80.00),
('QYASDE', 'Emily Davis', 8, 'A1', 100.00),
('1QAZD2', 'Laura Nelson', 8, 'A2', 89.00),
('5QAZD2', 'Henry Carter', 8, 'B1', 79.00), -- Fixed duplicate
('2QAZD2', 'Sarah Mitchell', 8, 'C2', 77.00),
('BMXND1', 'Daniel Perez', 8, 'C1', 94.00), -- Fixed duplicate
('BMXND2', 'Kimberly Roberts', 8, 'D1', 81.00),
('SS988D', 'Michael Johnson', 9, 'A2', 222.00),
('SS978D', 'Kevin Turner', 9, 'A1', 198.00),
('SS968D', 'Edward Phillips', 9, 'B2', 199.00),
('SS988D', 'Mary Campbell', 9, 'C1', 188.00),
('SS988E', 'Angela Parker', 9, 'C2', 177.00);




-- 3. Who flew the day before yesterday on the flight from JFK to LAX in seat B1?
select *
from ticket
         join flight f
              on ticket.flight_id = f.id
where seat_no = 'B1'
  and f.departure_airport_code = 'JFK'
  and f.arrival_airport_code = 'LAX'
  and f.departure_date::date = (now() - interval '2 days')::date;

-- 4. How many seats remained unoccupied on 2023-06-14 on flight AA1002?
select t2.count - t1.count
from (
         select f.aircraft_id, count(*)
         from ticket t
                  join flight f
                       on f.id = t.flight_id
         where f.flight_no = 'AA1002'
           and f.departure_date::date = '2023-06-14'
         group by f.aircraft_id) t1
         join (
    select aircraft_id, count(*)
    from seat
    group by aircraft_id) t2
              on t1.aircraft_id = t2.aircraft_id;

-- Check if ticket with id = 2000 exists
SELECT EXISTS(select 1 from ticket where id = 2000);

-- 2nd variant to find unoccupied seats on flight AA1002 on 2023-06-14
select s.seat_no
from seat s
where aircraft_id = 1
  and not exists(select t.seat_no
                 from ticket t
                          join flight f
                               on f.id = t.flight_id
                 where f.flight_no = 'AA1002'
                   and f.departure_date::date = '2023-06-14'
                     and s.seat_no = t.seat_no);

-- 3rd variant to find unoccupied seats on flight AA1002 on 2023-06-14
select aircraft_id, s.seat_no
from seat s
where aircraft_id = 1
    except
select f.aircraft_id, t.seat_no
from ticket t
         join flight f
              on f.id = t.flight_id
where f.flight_no = 'AA1002'
  and f.departure_date::date = '2023-06-14';

-- 5. Which 2 flights were the longest in duration?
select f.id,
       f.arrival_date,
       f.departure_date,
       f.arrival_date - f.departure_date
from flight f
order by (f.arrival_date - f.departure_date) DESC
limit 2;

-- 6. What is the maximum and minimum duration of flights between JFK and LAX and how many such flights have there been?
select
    first_value(f.arrival_date - f.departure_date) over (order by (f.arrival_date - f.departure_date) desc) max_value,
    first_value(f.arrival_date - f.departure_date) over (order by (f.arrival_date - f.departure_date)) min_value,
    count(*) OVER()
from flight f
         join airport a
              on a.code = f.arrival_airport_code
         join airport d
              on d.code = f.departure_airport_code
where a.city = 'Los Angeles'
  and d.city = 'New York'
limit 1;

-- 7. Which names are the most common and what share of all passengers do they represent?
select t.passenger_name,
       count(*),
       round(100.0 * count(*) / (select count(*) from ticket), 2) as percentage
from ticket t
group by t.passenger_name
order by 2 desc;

-- 8. List passenger names, how many tickets each has bought, and how much less this is than the passenger who bought the most tickets
select t1.*,
       first_value(t1.cnt) over () - t1.cnt as difference_from_max
from (
         select t.passenger_no,
                t.passenger_name,
                count(*) cnt
         from ticket t
         group by t.passenger_no, t.passenger_name
         order by 3 desc) t1;

-- 9. List the cost of all routes in descending order and show the difference in cost between each route and the next highest
select t1.*,
       COALESCE(lead(t1.sum_cost) OVER(order by t1.sum_cost), t1.sum_cost) - t1.sum_cost as difference_from_next
from (
         select t.flight_id,
                sum(t.cost) as sum_cost
         from ticket t
         group by t.flight_id
         order by 2 desc) t1;

-- Example of using EXCEPT with VALUES
values (1, '2'), (3, '4'), (5, '6'), (7, '8')
except
values (1, '2'), (2, '4'), (5, '6'), (7, '9');

-- Check if ticket with id = 29 exists
select id
from ticket
where id = 29;
