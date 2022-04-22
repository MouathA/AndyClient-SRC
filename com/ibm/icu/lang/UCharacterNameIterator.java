package com.ibm.icu.lang;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

class UCharacterNameIterator implements ValueIterator
{
    private UCharacterName m_name_;
    private int m_choice_;
    private int m_start_;
    private int m_limit_;
    private int m_current_;
    private int m_groupIndex_;
    private int m_algorithmIndex_;
    private static char[] GROUP_OFFSETS_;
    private static char[] GROUP_LENGTHS_;
    
    public boolean next(final Element element) {
        if (this.m_current_ >= this.m_limit_) {
            return false;
        }
        if (this.m_choice_ == 0 || this.m_choice_ == 2) {
            final int algorithmLength = this.m_name_.getAlgorithmLength();
            if (this.m_algorithmIndex_ < algorithmLength) {
                while (this.m_algorithmIndex_ < algorithmLength && (this.m_algorithmIndex_ < 0 || this.m_name_.getAlgorithmEnd(this.m_algorithmIndex_) < this.m_current_)) {
                    ++this.m_algorithmIndex_;
                }
                if (this.m_algorithmIndex_ < algorithmLength) {
                    final int algorithmStart = this.m_name_.getAlgorithmStart(this.m_algorithmIndex_);
                    if (this.m_current_ < algorithmStart) {
                        int limit_;
                        if (this.m_limit_ <= (limit_ = algorithmStart)) {
                            limit_ = this.m_limit_;
                        }
                        if (!this.iterateGroup(element, limit_)) {
                            ++this.m_current_;
                            return true;
                        }
                    }
                    if (this.m_current_ >= this.m_limit_) {
                        return false;
                    }
                    element.integer = this.m_current_;
                    element.value = this.m_name_.getAlgorithmName(this.m_algorithmIndex_, this.m_current_);
                    this.m_groupIndex_ = -1;
                    ++this.m_current_;
                    return true;
                }
            }
        }
        if (!this.iterateGroup(element, this.m_limit_)) {
            ++this.m_current_;
            return true;
        }
        if (this.m_choice_ == 2 && !this.iterateExtended(element, this.m_limit_)) {
            ++this.m_current_;
            return true;
        }
        return false;
    }
    
    public void reset() {
        this.m_current_ = this.m_start_;
        this.m_groupIndex_ = -1;
        this.m_algorithmIndex_ = -1;
    }
    
    public void setRange(final int start_, final int limit_) {
        if (start_ >= limit_) {
            throw new IllegalArgumentException("start or limit has to be valid Unicode codepoints and start < limit");
        }
        if (start_ < 0) {
            this.m_start_ = 0;
        }
        else {
            this.m_start_ = start_;
        }
        if (limit_ > 1114112) {
            this.m_limit_ = 1114112;
        }
        else {
            this.m_limit_ = limit_;
        }
        this.m_current_ = this.m_start_;
    }
    
    protected UCharacterNameIterator(final UCharacterName name_, final int choice_) {
        this.m_groupIndex_ = -1;
        this.m_algorithmIndex_ = -1;
        if (name_ == null) {
            throw new IllegalArgumentException("UCharacterName name argument cannot be null. Missing unames.icu?");
        }
        this.m_name_ = name_;
        this.m_choice_ = choice_;
        this.m_start_ = 0;
        this.m_limit_ = 1114112;
        this.m_current_ = this.m_start_;
    }
    
    private boolean iterateSingleGroup(final Element element, final int n) {
        // monitorenter(group_OFFSETS_ = UCharacterNameIterator.GROUP_OFFSETS_)
        // monitorenter(group_LENGTHS_ = UCharacterNameIterator.GROUP_LENGTHS_)
        final int groupLengths = this.m_name_.getGroupLengths(this.m_groupIndex_, UCharacterNameIterator.GROUP_OFFSETS_, UCharacterNameIterator.GROUP_LENGTHS_);
        while (this.m_current_ < n) {
            final int groupOffset = UCharacterName.getGroupOffset(this.m_current_);
            String value = this.m_name_.getGroupName(groupLengths + UCharacterNameIterator.GROUP_OFFSETS_[groupOffset], UCharacterNameIterator.GROUP_LENGTHS_[groupOffset], this.m_choice_);
            if ((value == null || value.length() == 0) && this.m_choice_ == 2) {
                value = this.m_name_.getExtendedName(this.m_current_);
            }
            if (value != null && value.length() > 0) {
                element.integer = this.m_current_;
                element.value = value;
                // monitorexit(group_LENGTHS_)
                // monitorexit(group_OFFSETS_)
                return false;
            }
            ++this.m_current_;
        }
        // monitorexit(group_LENGTHS_)
        // monitorexit(group_OFFSETS_)
        return true;
    }
    
    private boolean iterateGroup(final Element element, final int n) {
        if (this.m_groupIndex_ < 0) {
            this.m_groupIndex_ = this.m_name_.getGroup(this.m_current_);
        }
        while (this.m_groupIndex_ < this.m_name_.m_groupcount_ && this.m_current_ < n) {
            final int codepointMSB = UCharacterName.getCodepointMSB(this.m_current_);
            final int groupMSB = this.m_name_.getGroupMSB(this.m_groupIndex_);
            if (codepointMSB == groupMSB) {
                if (codepointMSB == UCharacterName.getCodepointMSB(n - 1)) {
                    return this.iterateSingleGroup(element, n);
                }
                if (!this.iterateSingleGroup(element, UCharacterName.getGroupLimit(groupMSB))) {
                    return false;
                }
                ++this.m_groupIndex_;
            }
            else if (codepointMSB > groupMSB) {
                ++this.m_groupIndex_;
            }
            else {
                int groupMin = UCharacterName.getGroupMin(groupMSB);
                if (groupMin > n) {
                    groupMin = n;
                }
                if (this.m_choice_ == 2 && !this.iterateExtended(element, groupMin)) {
                    return false;
                }
                this.m_current_ = groupMin;
            }
        }
        return true;
    }
    
    private boolean iterateExtended(final Element element, final int n) {
        while (this.m_current_ < n) {
            final String extendedOr10Name = this.m_name_.getExtendedOr10Name(this.m_current_);
            if (extendedOr10Name != null && extendedOr10Name.length() > 0) {
                element.integer = this.m_current_;
                element.value = extendedOr10Name;
                return false;
            }
            ++this.m_current_;
        }
        return true;
    }
    
    static {
        UCharacterNameIterator.GROUP_OFFSETS_ = new char[33];
        UCharacterNameIterator.GROUP_LENGTHS_ = new char[33];
    }
}
