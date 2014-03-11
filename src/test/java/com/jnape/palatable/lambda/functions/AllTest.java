package com.jnape.palatable.lambda.functions;

import com.jnape.palatable.lambda.MonadicFunction;
import com.jnape.palatable.lambda.Predicate;
import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.runners.Traits;
import org.junit.Test;
import org.junit.runner.RunWith;
import testsupport.traits.EmptyIterableSupport;

import static com.jnape.palatable.lambda.builtin.monadic.Always.always;
import static com.jnape.palatable.lambda.functions.All.all;
import static com.jnape.palatable.lambda.functions.Repeat.repeat;
import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Traits.class)
public class AllTest {

    private static final MonadicFunction<? super Number, Boolean> EVEN = new Predicate<Number>() {
        @Override
        public Boolean apply(Number number) {
            return number.doubleValue() % 2 == 0;
        }
    };

    @TestTraits({EmptyIterableSupport.class})
    public MonadicFunction<Iterable<Object>, Boolean> createTestSubject() {
        return all(always(true));
    }

    @Test
    public void trueIfAllElementsMatchPredicate() {
        Iterable<Integer> numbers = asList(2, 4, 6, 8);
        assertThat(all(EVEN, numbers), is(true));
    }

    @Test
    public void falseIfAnyElementsFailPredicate() {
        Iterable<Integer> numbers = asList(1, 2, 4, 6, 8);
        assertThat(all(EVEN, numbers), is(false));
    }

    @Test
    public void terminatesIterationImmediatelyUponPredicateFailure() {
        assertThat(all(EVEN, repeat(1)), is(false));
    }
}
