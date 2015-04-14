/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.tests.components.grid.basicfeatures.server;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.vaadin.testbench.parallel.Browser;
import com.vaadin.tests.components.grid.basicfeatures.GridBasicFeaturesTest;

public class GridSidebarThemeTest extends GridBasicFeaturesTest {

    @Test
    public void testValo() throws Exception {
        runTestSequence("valo");
    }

    private void runTestSequence(String theme) throws IOException {
        openTestURL("theme=" + theme);
        if (getDesiredCapabilities().getBrowserName().equals(
                Browser.CHROME.getDesiredCapabilities().getBrowserName())) {
            waitUntil(ExpectedConditions.elementToBeClickable(By.id("menu")), 2);
            getDriver().findElement(By.id("menu")).click();
            selectMenu("Columns");
            selectMenu("All columns hidable");
            waitUntilLoadingIndicatorNotVisible();
        } else {
            selectMenuPath("Component", "Columns", "All columns hidable");
        }

        compareScreen(theme + "|SidebarClosed");
        getSidebarOpenButton().click();

        compareScreen(theme + "|SidebarOpen");

        new Actions(getDriver()).moveToElement(getColumnHidingToggle(2), 5, 5)
                .perform();

        compareScreen(theme + "|OnMouseOverNotHiddenToggle");

        getColumnHidingToggle(2).click();
        getColumnHidingToggle(3).click();
        getColumnHidingToggle(6).click();

        new Actions(getDriver()).moveToElement(getSidebarOpenButton())
                .perform();
        ;

        compareScreen(theme + "|TogglesTriggered");

        new Actions(getDriver()).moveToElement(getColumnHidingToggle(2))
                .perform();
        ;

        compareScreen(theme + "|OnMouseOverHiddenToggle");

        getSidebarOpenButton().click();

        compareScreen(theme + "|SidebarClosed2");
    }

    @Override
    public List<DesiredCapabilities> getBrowsersToTest() {
        // phantom JS looks wrong from the beginning, so not tested
        return getBrowsersExcludingPhantomJS();
    }
}
