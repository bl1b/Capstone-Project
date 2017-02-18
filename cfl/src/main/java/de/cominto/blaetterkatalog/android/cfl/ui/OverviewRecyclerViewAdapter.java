/*******************************************************************************
 * Copyright (c) 2017 Jan Grünewald.
 * All rights reserved.
 * This project is licensed under NPOSL-3.0 (https://opensource.org/licenses/NPOSL-3.0).
 ******************************************************************************/

package de.cominto.blaetterkatalog.android.cfl.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.cominto.blaetterkatalog.android.cfl.R;
import de.cominto.blaetterkatalog.android.cfl.model.atom.OverviewDisplayItemProvider;

/**
 * Class OverviewRecyclerViewAdapter.
 * TODO: Add Description
 *
 * @author Jan Grünewald (2017)
 * @version 1.0.0
 */
public class OverviewRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int HOLDER_TYPE_SECTION = 0;
    public static final int HOLDER_TYPE_ELEMENT = 1;

    private final OverviewDisplayItemProvider itemProvider;


    public OverviewRecyclerViewAdapter(final OverviewDisplayItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView;

        switch (viewType) {
            case HOLDER_TYPE_SECTION:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_section, parent, false);
                return new ViewHolderSection(layoutView);
            case HOLDER_TYPE_ELEMENT:
                layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_item, parent, false);
                return new ViewHolderElement(layoutView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final OverviewDisplayItem item = itemAtPosition(position);
        if (item != null) {
            switch (holder.getItemViewType()) {
                case HOLDER_TYPE_SECTION:
//                    ((ViewHolderSection) holder).tvSectionTitle.setText(item.getTitle());
                    ((ViewHolderSection) holder).vgContainer.setBackgroundColor(item.getColor());
                    break;
                case HOLDER_TYPE_ELEMENT:
                    if (item.getImageFile() != null && item.getImageFile().isFile()) {
                        ((ViewHolderElement) holder).ivItemImage.setImageDrawable(Drawable.createFromPath(item.getImageFile().getAbsolutePath()));
                        ((ViewHolderElement) holder).ivItemImage.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderElement) holder).ivItemImage.setVisibility(View.GONE);
                    }

                    if (item.getTitle() != null && !item.getTitle().isEmpty()) {
                        ((ViewHolderElement) holder).tvItemTitle.setText(item.getTitle());
                        ((ViewHolderElement) holder).tvItemTitle.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderElement) holder).tvItemTitle.setVisibility(View.GONE);
                    }

                    if (item.getDescription() != null && !item.getDescription().isEmpty()) {
                        ((ViewHolderElement) holder).tvItemDescription.setText(item.getDescription());
                        ((ViewHolderElement) holder).tvItemDescription.setVisibility(View.VISIBLE);
                    } else {
                        ((ViewHolderElement) holder).tvItemDescription.setVisibility(View.GONE);
                    }

                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemProvider.getNumberOfDisplayItems();
    }

    @Override
    public int getItemViewType(int position) {
        OverviewDisplayItem item = itemAtPosition(position);
        return (item != null
                && item.getItemType() == OverviewDisplayItem.DisplayItemType.SECTION)
                ? HOLDER_TYPE_SECTION : HOLDER_TYPE_ELEMENT;
    }

    private OverviewDisplayItem itemAtPosition(int position) {
        if (itemProvider != null
                && itemProvider.provideDisplayItems() != null
                && itemProvider.provideDisplayItems().size() > position
                && position >= 0) {
            return itemProvider.provideDisplayItems().get(position);
        }

        return null;
    }

    private class ViewHolderElement extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemTitle;
        TextView tvItemDescription;

        ViewHolderElement(View itemView) {
            super(itemView);

            ivItemImage = (ImageView) itemView.findViewById(R.id.overview_item_image);
            tvItemTitle = (TextView) itemView.findViewById(R.id.overview_item_title);
            tvItemDescription = (TextView) itemView.findViewById(R.id.overview_item_description);
        }
    }

    private class ViewHolderSection extends RecyclerView.ViewHolder {
        TextView tvSectionTitle;
        ViewGroup vgContainer;

        ViewHolderSection(View itemView) {
            super(itemView);

            vgContainer = (ViewGroup) itemView.findViewById(R.id.overview_section_container);
            tvSectionTitle = (TextView) itemView.findViewById(R.id.overview_section_tv_section);

            StaggeredGridLayoutManager.LayoutParams layoutParams = itemView.getLayoutParams() != null
                    ? (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams()
                    : new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParams.setFullSpan(true);
        }
    }
}
