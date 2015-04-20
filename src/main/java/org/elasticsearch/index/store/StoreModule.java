/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.store;

import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.ShardLock;
import org.elasticsearch.index.shard.ShardPath;

/**
 *
 */
public class StoreModule extends AbstractModule {

    public static final String DISTIBUTOR_KEY = "index.store.distributor";
    public static final String LEAST_USED_DISTRIBUTOR = "least_used";
    public static final String RANDOM_WEIGHT_DISTRIBUTOR = "random";

    private final Settings settings;

    private final ShardLock lock;
    private final Store.OnClose closeCallback;
    private final ShardPath path;
    private final Class<? extends DirectoryService> shardDirectory;


    public StoreModule(Settings settings,  Class<? extends DirectoryService> shardDirectory, ShardLock lock, Store.OnClose closeCallback, ShardPath path) {
        this.shardDirectory = shardDirectory;
        this.settings = settings;
        this.lock = lock;
        this.closeCallback = closeCallback;
        this.path = path;
    }

    @Override
    protected void configure() {
        bind(DirectoryService.class).to(shardDirectory).asEagerSingleton();
        bind(Store.class).asEagerSingleton();
        bind(ShardLock.class).toInstance(lock);
        bind(Store.OnClose.class).toInstance(closeCallback);
        bind(ShardPath.class).toInstance(path);
    }
}
