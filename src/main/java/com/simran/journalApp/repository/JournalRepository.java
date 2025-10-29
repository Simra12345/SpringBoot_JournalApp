package com.simran.journalApp.repository;

import com.simran.journalApp.entities.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<Journal, ObjectId> {}
