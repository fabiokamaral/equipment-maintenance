package com.challenge.equipmentmaintenance.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "databaseSequence")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseSequence {

	@Id
	private String id;

	private long seq;

}