CREATE TABLE Contact (
  id            NUMBER(10)   NOT NULL,
  last_name    	varchar(50)  NOT NULL,
  first_name    varchar(50)  NOT NULL,
  email    	varchar(50)  NOT NULL,
  phone         varchar(50)  NOT NULL,
  CONSTRAINT Contact_ID_pk PRIMARY KEY (id)
);


COMMENT ON table Contact IS 'My first table.';
COMMENT ON column Contact.id IS 'The ID.';
COMMENT ON column Contact.last_name IS 'The name.';
COMMENT ON column Contact.first_name IS 'The fisrt name.';
COMMENT ON column Contact.email IS 'The mail.';
COMMENT ON column Contact.phone IS 'The phone.';
