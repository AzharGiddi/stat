package com.automation.core.utils.ui;

import com.google.common.base.Function;

/**
 * Models a condition that might reasonably be expected to eventually evaluate
 * to something that is neither null nor false. Examples would include
 * determining if a web page has loaded or that an element is visible.
 * <p>
 * 
 * @param <T>
 *            The return type
 */
public interface ExpectedCondition<O, T> extends Function<O, T> {

}
