ALTER TABLE event
ADD CONSTRAINT event_producer_fkey
FOREIGN KEY (producer)
REFERENCES producer(id)
ON DELETE CASCADE;