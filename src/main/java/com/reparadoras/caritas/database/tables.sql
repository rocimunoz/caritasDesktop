-- PEOPLE
CREATE TABLE C_PEOPLE (
  id int(11) NOT NULL AUTO_INCREMENT,
  dni varchar(255) DEFAULT NULL,
  name varchar(255) NOT NULL,
  sex char(1) NOT NULL,
  first_surname varchar(255) NOT NULL,
  date_born date DEFAULT NULL,
  active tinyint(1) DEFAULT '1',
  passport varchar(11) NOT NULL,
  second_surname varchar(255) NOT NULL,
  create_date date DEFAULT NULL,
  reactivate_date date DEFAULT NULL,
  country varchar(20) NOT NULL,
  nationality varchar(20) DEFAULT NULL,
  year_to_spain smallint(6) DEFAULT NULL,
  civil_status varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;


-- ADDRESS
CREATE TABLE C_ADDRESS (
  id int(11) NOT NULL AUTO_INCREMENT,
  town varchar(20) DEFAULT NULL,
  street varchar(20) DEFAULT NULL,
  gate varchar(20) DEFAULT NULL,
  floor varchar(20) DEFAULT NULL,
  telephone varchar(20) DEFAULT NULL,
  telephone_contact varchar(20) DEFAULT NULL,
  postal_code varchar(20) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- HOME

-- FAMILY

-- TYPE_FAMILY

-- RELATIVE



