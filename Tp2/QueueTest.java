package queue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class QueueTest {

@Test public void test01QueueShouldBeEmptyWhenCreated() {
    assertTrue( new Queue().isEmpty() );
  }

  @Test public void test02AddElementsToTheQueue() {
    assertFalse( addObjectToNewQueue( anyObject ).isEmpty() );
  }

  @Test public void test03AddedElementsIsAtHead() {
    assertEquals( anyObject, addObjectToNewQueue( anyObject ).head() );
  }

  @Test public void test04TakeRemovesElementsFromTheQueue() {
    Queue queue = addObjectToNewQueue( anyObject );
    queue.take();
    
    assertTrue( queue.isEmpty() );
  }

  @Test public void test05TakeReturnsLastAddedObject() {
    assertEquals( anyObject, addObjectToNewQueue( anyObject ).take() );
  }

  @Test public void test06QueueBehavesFIFO() {
    Queue queue = addFirstAndSecondObject();
    
    assertEquals( queue.take(), firstObject );
    assertEquals( queue.take(), secondObject );
    assertTrue( queue.isEmpty() );
  }

  @Test public void test07HeadReturnsFirstAddedObject() {
    assertEquals( addFirstAndSecondObject().head(), firstObject );
  }

  @Test public void test08HeadDoesNotRemoveObjectFromQueue() {
    Queue queue = addObjectToNewQueue( anyObject );
    
    assertEquals( 1, queue.size() );
    queue.head();
    assertEquals( 1, queue.size() );
  }

  @Test public void test09SizeRepresentsObjectInTheQueue() {
	assertEquals( 2, addFirstAndSecondObject().size() );
  }

  @Test public void test10CanNotTakeWhenThereAreNoObjectsInTheQueue() {
	    assertThrowsLike( () -> new Queue().take());
	  }
	  
  @Test public void test09CanNotTakeWhenThereAreNoObjectsInTheQueueAndTheQueueHadObjects() {
	    Queue queue = addObjectToNewQueue( anyObject );
	    queue.take();
	    assertThrowsLike( () -> queue.take());
	  }

 @Test public void test10CanNotHeadWhenThereAreNoObjectsInTheQueue() {
	    assertThrowsLike( () -> new Queue().head());
	  }
  private void assertThrowsLike(Executable executable) {
	  assertEquals(estadoVacio.queueIsEmpty, assertThrows(Error.class,executable).getMessage());
	  }
  
  private Queue addObjectToNewQueue( Object object ) {
	  Queue queue = new Queue();
	  queue.add( object );
	  return queue;
  }
  
  private Queue addFirstAndSecondObject() {
	  Queue queue = new Queue();
      queue.add( firstObject );
      queue.add( secondObject );
	  return queue;
  }
  private static final String anyObject = "Something";
  private static final String firstObject = "First";
  private static final String secondObject = "Second";
  
}