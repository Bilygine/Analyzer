package com.bilygine.analyzer.service;

import com.bilygine.analyzer.analyze.Analyze;
import com.bilygine.analyzer.analyze.Status;
import com.bilygine.analyzer.entity.error.AnalyzerError;
import com.bilygine.analyzer.entity.json.model.AnalyzeModel;
import com.bilygine.analyzer.entity.json.output.AnalyzeOutput;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class DatabaseService {

	private static FirebaseDatabase database;
	private static final String DATABASE_URL = "https://etna-gpe.firebaseio.com/";
	private static final String ROOT_REF = "queue";
	private static DatabaseReference reference;
	private static Map<String, AnalyzeModel> analyzes = new HashMap<String, AnalyzeModel>();

	public void connect() {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/esteban/.google/google_application_credentials/ETNA-GPE.json");
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setServiceAccount(inputStream)
					.setDatabaseUrl(DATABASE_URL)
					.build();
			FirebaseApp.initializeApp(options);
			database  = FirebaseDatabase.getInstance();
			reference = database.getReference(ROOT_REF);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		listen();
	}

	public String createAnalyze(AnalyzeModel model) {
		reference.child(model.id).setValue(model);
		return model.id;
	}

	public void updateStatus(String id, Status status) {
		reference.child(id).child("status").setValue(status.formatted());
	}

	public void updateStep(String id, Status status) {
		long timestamp = System.currentTimeMillis();
		reference.child(id).child("steps/" + timestamp).setValue(status.formatted());
	}

	public void listen() {
		reference.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				dataSnapshot.getChildren().forEach((c) -> {
					AnalyzeModel model = c.getValue(AnalyzeModel.class);
					if (model == null)
						throw new AnalyzerError("New analyze model is null");
					analyzes.put(c.getKey(), model);
					System.out.println(model.toString());
				});
			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				System.out.println("The read failed: " + databaseError.getCode());
			}
		});
	}

	public AnalyzeModel findAnalyzeById(String id) {
		return analyzes.containsKey(id) ? analyzes.get(id) : null;
	}
}
