package net.i2cat.nexus.resources.tests.capability;

import junit.framework.Assert;
import net.i2cat.nexus.resources.ILifecycle;
import net.i2cat.nexus.resources.IResource;
import net.i2cat.nexus.resources.Resource;
import net.i2cat.nexus.resources.ResourceException;
import net.i2cat.nexus.resources.action.ActionSet;
import net.i2cat.nexus.resources.descriptor.CapabilityDescriptor;
import net.i2cat.nexus.resources.descriptor.Information;
import net.i2cat.nexus.resources.profile.IProfile;
import net.i2cat.nexus.resources.tests.capability.mocks.MockAction;
import net.i2cat.nexus.resources.tests.capability.mocks.MockActionTwo;
import net.i2cat.nexus.resources.tests.capability.mocks.MockCapAction;
import net.i2cat.nexus.resources.tests.capability.mocks.MockCapActionTwo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

public class CapabilityTest {
	private static Log				log						= LogFactory.getLog(CapabilityTest.class);
	private static MockCapability	capability				= null;
	private static IProfile			profile;

	private static String			actionIdMock			= "actionMock";
	private static String			actionIdCapabilityTwo	= "actionCapabilityTwo";
	private static String			actionIdCapability		= "actionCapability";

	@BeforeClass
	public static void setUp() {

		/* init Profile */
		ActionSet actionSet = new ActionSet();
		MockAction mockAction = new MockAction();
		actionSet.putAction(actionIdMock, mockAction.getClass());

		MockActionTwo mockActionTwo = new MockActionTwo();
		actionSet.putAction(actionIdCapabilityTwo, mockActionTwo.getClass());

		capability = new MockCapability(getMockCapabilityDescriptor());

		// profile = new MockProfile();
		// profile.addActionSetForCapability(actionSet, capability.getCapabilityInformation().getType());

		Resource resource = new Resource();
		// resource.setProfile(profile);

		capability.setResource(resource);

		/* init actionSet */
		ActionSet actionSetCapability = new ActionSet();
		MockCapAction actionCapability = new MockCapAction();
		actionSetCapability.putAction(actionIdCapability, actionCapability.getClass());

		MockCapActionTwo actionCapabilityTwo = new MockCapActionTwo();
		actionSetCapability.putAction(actionIdCapabilityTwo, actionCapabilityTwo.getClass());
		capability.setActionSet(actionSetCapability);

	}

	private static CapabilityDescriptor getMockCapabilityDescriptor() {
		CapabilityDescriptor capabilityDescriptor = new CapabilityDescriptor();
		Information information = new Information();
		information.setName("Mock capability");
		information.setType("Mock");
		information.setVersion("0.0.1");
		capabilityDescriptor.setCapabilityInformation(information);
		return capabilityDescriptor;
	}

	@Test
	public void printInfo() {
		log.info(capability.toString());
	}

	@Test
	public void testInitialize() throws ResourceException {
		capability.initialize();
		Assert.assertEquals(ILifecycle.State.INITIALIZED, capability.getState());
		MockCapability mockCapability = capability;
		Assert.assertEquals(mockCapability.getInternalCall(), "initialize");
	}

	@Test
	public void testActivate() throws ResourceException {
		capability.activate();
		Assert.assertEquals(ILifecycle.State.ACTIVE, capability.getState());
		MockCapability mockCapability = capability;
		Assert.assertEquals(mockCapability.getInternalCall(), "activate");
	}

	// @Test FIXME need to test with well formed ->IProtocolSessionManager psm
	// public void testCreateAction() throws ResourceException {
	//
	// Action action;
	// IProtocolSessionManager psm = new ProtocolSessionManager("deviceID");
	// try {
	// log.info("INFO: Checking action Capability");
	// action = capability.createAction(actionIdCapability);
	// action.execute(psm);
	//
	// Assert.assertTrue(action instanceof MockCapAction);
	//
	// log.info("INFO: Checking action actionID");
	// action = capability.createAction(actionIdMock);
	// action.execute(psm);
	// Assert.assertTrue(action instanceof MockAction);
	//
	// log.info("INFO: Checking action capability two, it must use mock action two, because of Profile");
	// action = capability.createAction(actionIdCapabilityTwo);
	// action.execute(psm);
	//
	// Assert.assertTrue(action instanceof MockActionTwo);
	//
	// } catch (ActionException e) {
	// Assert.fail(e.getLocalizedMessage());
	// }
	//
	// }

	@Test
	public void testDeactivate() throws ResourceException {
		capability.deactivate();
		Assert.assertEquals(ILifecycle.State.INACTIVE, capability.getState());
		MockCapability mockCapability = capability;
		Assert.assertEquals(mockCapability.getInternalCall(), "deactivate");
	}

	@Test
	public void testShutdown() throws ResourceException {
		capability.shutdown();
		Assert.assertEquals(ILifecycle.State.SHUTDOWN, capability.getState());
		MockCapability mockCapability = capability;
		Assert.assertEquals(mockCapability.getInternalCall(), "shutdown");
	}
}