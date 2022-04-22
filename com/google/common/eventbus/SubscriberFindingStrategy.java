package com.google.common.eventbus;

import com.google.common.collect.*;

interface SubscriberFindingStrategy
{
    Multimap findAllSubscribers(final Object p0);
}
