/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ui;

import android.content.Context;
import android.text.Html;
import de.cominto.blaetterkatalog.android.cfl.model.DataSourceEntry;
import de.cominto.blaetterkatalog.android.cfl.model.Section;

import java.io.File;

/**
 * Class OverviewDisplayItem.
 * This is the data-representation of an item that is shown on the detail
 * fragment of the Overview-Activity.
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class OverviewDisplayItem {
    public enum DisplayItemType {
        ELEMENT, SECTION;
    }

    private final DisplayItemType itemType;

    private String title;
    private String description;
    private File imageFile;
    private int color;

    public static OverviewDisplayItem createFromSection(Section section, Context context) {
        OverviewDisplayItem item = new OverviewDisplayItem(DisplayItemType.SECTION);
        item.title = section.getName(context);
        item.color = section.getColor();
        return item;
    }

    public static OverviewDisplayItem createFromDataSource(DataSourceEntry cflDataSourceEntry) {
        OverviewDisplayItem item = new OverviewDisplayItem(DisplayItemType.ELEMENT);
        item.title = Html.fromHtml(cflDataSourceEntry.getTitle()).toString();
        item.description = cflDataSourceEntry.getDescription();
        item.imageFile = cflDataSourceEntry.getDetailIcon();
        return item;
    }

    private OverviewDisplayItem(DisplayItemType itemType) {
        this.itemType = itemType;
    }

    public DisplayItemType getItemType() {
        return itemType;
    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public File getImageFile() {
        return imageFile;
    }
}
