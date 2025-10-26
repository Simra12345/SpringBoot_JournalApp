package com.simran.journalApp.repository;

import com.simran.journalApp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<Journal, ObjectId> {}