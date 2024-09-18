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

CREATE TABLE Material (
	unitCost DOUBLE PRECISION NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    transportCost DOUBLE PRECISION NOT NULL,
    qualityCoefficient DOUBLE PRECISION NOT NULL
) INHERITS (Component);

CREATE TABLE Labor (
    hourlyRate DOUBLE PRECISION NOT NULL,
    hoursWorked DOUBLE PRECISION NOT NULL,
    workerProductivity DOUBLE PRECISION NOT NULL
) INHERITS (Component);

CREATE TABLE Estimates (
    id SERIAL PRIMARY KEY,
    project_id INT,
    estimatedAmount DOUBLE PRECISION NOT NULL,
    issueDate DATE NOT NULL,
    validityDate DATE NOT NULL,
    accepted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (project_id) REFERENCES Projects(id) ON DELETE CASCADE
);