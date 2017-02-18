/*******************************************************************************
 * Copyright (c) 2016 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.model.atom;

import android.text.TextUtils;

import de.cominto.blaetterkatalog.android.cfl.model.DataSourceEntry;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.cominto.blaetterkatalog.android.util.DateUtil;
import timber.log.Timber;

/**
 * Class AtomFeedEntry.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2016)
 * @version 1.0.0
 */
@Root(name = "entry", strict = false)
public class AtomFeedEntry implements DataSourceEntry {
    @Element(name = "id")
    private String id = "";

    @Element(name = "title")
    private AtomFeedText title = new AtomFeedText();

    @Element(name = "updated")
    private String updated = "";

    @ElementList(required = false, inline = true)
    private List<AtomFeedAuthor> authors = new ArrayList<>();

    @Element(name = "content", required = false)
    private AtomFeedText content = new AtomFeedText();

    @ElementList(required = false, inline = true)
    private List<AtomFeedLink> links = new ArrayList<>();

    @Element(required = false, name = "summary")
    private AtomFeedText summary = new AtomFeedText();

    @ElementList(required = false, inline = true)
    private List<AtomFeedCategory> categories = new ArrayList<>();

    @ElementList(required = false, inline = true, name = "contributor")
    private List<AtomFeedContributor> contributors = new ArrayList<>();

    @Element(required = false, name = "published")
    private String published = "";

    public String getId() {
        return id;
    }

    public AtomFeedText getOriginalTitle() {
        return title;
    }

    public String getUpdated() {
        return updated;
    }

    public List<AtomFeedAuthor> getAuthors() {
        return authors;
    }

    public AtomFeedText getOriginalContent() {
        return content;
    }

    public List<AtomFeedLink> getLinks() {
        return links;
    }

    public AtomFeedText getSummary() {
        return summary;
    }

    public List<AtomFeedCategory> getCategories() {
        return categories;
    }

    public List<AtomFeedContributor> getContributors() {
        return contributors;
    }

    public String getPublished() {
        return published;
    }

    @Override
    public File getOverviewIcon() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return getId();
    }

    @Override
    public Date getDate() {
        Date result = new Date();
        try {
            result = DateUtil.parseRFC3339Date(getUpdated());
        } catch (ParseException e) {
            Timber.e(e, "Failed parsing RFC 3339-Date from %s", getUpdated());
            result = new Date();
        }
        return result;
    }

    @Override
    public String getTitle() {
        return getOriginalTitle().getText();
    }

    @Override
    public String getDescription() {
        return getSummary().getText();
    }

    @Override
    public String getContent() {
        return getOriginalContent().getText();
    }

    @Override
    public File getDetailIcon() {
        return null;
    }

    @Override
    public String asJson() {
        String jsonString = "{ ";
        jsonString += "\"identifier\": \"" + getIdentifier() + "\", ";
        jsonString += "\"date\": " + getDate().getTime() + ", ";
        jsonString += "\"title\": \"" + TextUtils.htmlEncode(getTitle()) + "\", ";
        jsonString += "\"description\": \"" + TextUtils.htmlEncode(getDescription()) + "\", ";
        jsonString += "\"content\": \"" + TextUtils.htmlEncode(getContent()) + "\"";
        jsonString += " }";
        return jsonString;
    }
}
