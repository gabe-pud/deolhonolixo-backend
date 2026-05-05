CREATE TABLE trucks (
    id BIGSERIAL PRIMARY KEY,
    license_plate VARCHAR(7) NOT NULL,
    status VARCHAR(255),
    route_start TIMESTAMP WITH TIME ZONE,
    route_end TIMESTAMP WITH TIME ZONE,
    route_id VARCHAR(255)
)