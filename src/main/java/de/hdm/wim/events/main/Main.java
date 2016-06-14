package de.hdm.wim.events.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.drools.compiler.lang.ParseException;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import de.hdm.wim.events.interceptor.EventStorageInterceptor;
import de.hdm.wim.events.mocks.UserInterfaceMock;
import de.hdm.wim.events.model.Event;
import de.hdm.wim.events.model.Participant;
import de.hdm.wim.events.model.SpeechToken;
import de.hdm.wim.token.Token;

public class Main {

	

}
