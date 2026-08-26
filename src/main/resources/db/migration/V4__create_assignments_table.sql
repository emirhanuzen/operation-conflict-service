CREATE TABLE assignments (
    id BIGSERIAL PRIMARY KEY,
    service_date DATE NOT NULL,
    task_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    vehicle_id BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT fk_assignment_task FOREIGN KEY (task_id) REFERENCES operation_tasks(id),
    CONSTRAINT fk_assignment_driver FOREIGN KEY (driver_id) REFERENCES drivers(id),
    CONSTRAINT fk_assignment_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id)
);