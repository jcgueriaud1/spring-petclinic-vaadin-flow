package org.springframework.samples.petclinic.service;

import com.deque.html.axecore.results.AxeResults;

public interface DataService {
	void saveA11yResults(String pageName, AxeResults results);
}
