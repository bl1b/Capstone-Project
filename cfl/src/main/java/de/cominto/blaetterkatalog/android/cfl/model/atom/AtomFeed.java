/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AtomFeed.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
@Root(name = "feed", strict = false)
public class AtomFeed {
    @Element(name = "id")
    private String id;

    @Element(name = "title")
    private AtomFeedText title = new AtomFeedText();

    @Element(name = "updated")
    private String updated = "";

    @ElementList(required = false, inline = true)
    private List<AtomFeedEntry> entries = new ArrayList<>();

    @ElementList(required = false, inline = true)
    private List<AtomFeedAuthor> authors = new ArrayList<>();

    @ElementList(required = false, inline = true)
    private List<AtomFeedLink> links = new ArrayList<>();

    @ElementList(required = false, inline = true)
    private List<AtomFeedCategory> categories = new ArrayList<>();

    @ElementList(required = false, inline = true)
    private List<AtomFeedContributor> contributors = new ArrayList<>();

    @Element(required = false)
    private AtomFeedGenerator generator = new AtomFeedGenerator();

    @Element(required = false, name = "icon")
    private String icon = "";

    @Element(required = false, name = "rights")
    private AtomFeedText rights = new AtomFeedText();

    @Element(required = false, name = "subtitle")
    private String subtitle = "";

    public String getId() {
        return id;
    }

    public AtomFeedText getTitle() {
        return title;
    }

    public String getUpdated() {
        return updated;
    }

    public List<AtomFeedEntry> getEntries() {
        return entries;
    }

    public List<AtomFeedAuthor> getAuthors() {
        return authors;
    }

    public List<AtomFeedLink> getLinks() {
        return links;
    }

    public List<AtomFeedCategory> getCategories() {
        return categories;
    }

    public List<AtomFeedContributor> getContributors() {
        return contributors;
    }

    public AtomFeedGenerator getGenerator() {
        return generator;
    }

    public String getIcon() {
        return icon;
    }

    public AtomFeedText getRights() {
        return rights;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
