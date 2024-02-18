package sa.alrajhi.retail.intent.analyzer.service;

import sa.alrajhi.retail.intent.analyzer.model.Intent;

import java.util.List;

public interface IntentLoaderService {
    List<Intent> getIntents();
}
