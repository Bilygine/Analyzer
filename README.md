# Bilygine Analyzer

Bilygine Analyzer can process audio file and products precious data 

# Get started

    /** Create steps list */  
    List<Step> steps = new ArrayList<>();
      
    /** Step - 1 | Transcription with Google */  
    steps.add(new GoogleTranscriptionStep("gs://pathToMyFile"));  
      
    /** Analyze **/  
    Analyze analyze = new DefaultAnalyze(steps);  
    analyze.run();
# Roadmap

 - [x] Analyzer Core ***05/10/2018***
 - [ ] Steps
	 - [ ] Transcription Step implemented with Google Speech to Text
	 - [ ] Unique Key associate to each line
	 - [ ] Sound volume processing
- [ ] Analyzer self-service from Web application
