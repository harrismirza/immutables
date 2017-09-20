package org.immutables.mongo.fixture;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.immutables.check.Checkers.check;

public class SimpleInserterTest {

  @Rule
  public final MongoContext context = MongoContext.create();

  private final ItemRepository repository = new ItemRepository(context.setup());

  @Test
  public void insertOne() throws Exception {
    Item item1 = ImmutableItem.of("i1");
    repository.insert(item1).getUnchecked();

    check(repository.findAll().fetchAll().getUnchecked()).hasSize(1);
  }

  @Test
  public void insertOne2() throws Exception {
    repository.insert(Collections.singleton(ImmutableItem.of("i1"))).getUnchecked();
    check(repository.findAll().fetchAll().getUnchecked()).hasSize(1);
  }


  /**
   * This test fails on real mongo: v3.4
   */
  @Test
  @Ignore
  public void insertMultiple() throws Exception {
    Item item1 = ImmutableItem.of("i1");
    Item item2 = ImmutableItem.of("i2");

    repository.insert(Arrays.asList(item1, item2)).getUnchecked();

    check(repository.findAll().fetchAll().getUnchecked()).hasContentInAnyOrder(item1, item2);
  }

}
