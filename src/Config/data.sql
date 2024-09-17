CREATE TABLE Clients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(15),
    isProfessional BOOLEAN DEFAULT FALSE
);

CREATE TYPE ProjectStatus AS ENUM ('ONGOING', 'COMPLETED', 'CANCELLED');

CREATE TABLE Projects (
    id SERIAL PRIMARY KEY,
    client_id INT,
    projectName VARCHAR(255) NOT NULL,
    profitMargin DOUBLE PRECISION NOT NULL,
    totalCost DOUBLE PRECISION NOT NULL,
    projectStatus ProjectStatus NOT NULL,
    FOREIGN KEY (client_id) REFERENCES Clients(id) ON DELETE CASCADE
);
CREATE TABLE Component (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    typeComponent VARCHAR(50) NOT NULL,
    tvaRate DOUBLE PRECISION NOT NULL
);