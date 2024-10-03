/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.portal.service.prefs;

import fr.paris.lutece.portal.service.cache.AbstractCacheableService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Cache service for {@link BaseUserPreferencesServiceImpl}
 */
@ApplicationScoped
public class BaseUserPreferencesCacheService extends AbstractCacheableService<String,String>
{
    private static final String CACHE_SERVICE_NAME = "BaseUserPreferencesCacheService";
    private static final String CONSTANT_UNDERSCORE = "_";

    @PostConstruct
    public void init( )
    {
        initCache( CACHE_SERVICE_NAME, String.class, String.class);
    }
   
    /**
     * {@inheritDoc}
     */
    @Override
    public String getName( )
    {
        return CACHE_SERVICE_NAME;
    }

    /**
     * Get the key in the cache of a preference of a user
     * 
     * @param strUserId
     *            The id of the user
     * @param strKey
     *            The preference key
     * @return The key in the cache of the preference of the user
     */
    public String getCacheKey( String strUserId, String strKey )
    {
        return strUserId + CONSTANT_UNDERSCORE + strKey;
    }

    /**
     * Remove every values stored in cache for a given user
     * 
     * @param strUserId
     *            The user id to remove from cache
     */
    public void removeCacheValuesOfUser( String strUserId )
    {
        if ( StringUtils.isNotEmpty( strUserId ) )
        {
            String strPrefix = strUserId + CONSTANT_UNDERSCORE;
            List<String> listKeysToRemove = new ArrayList<>( );

            for ( String strKey : getKeys( ) )
            {
                if ( strKey.startsWith( strPrefix ) )
                {
                    listKeysToRemove.add( strKey );
                }
            }

            for ( String strKey : listKeysToRemove )
            {
                remove( strKey );
            }
        }
    }
    /**
     * This method observes the initialization of the {@link ApplicationScoped} context.
     * It ensures that this CDI beans are instantiated at the application startup.
     *
     * <p>This method is triggered automatically by CDI when the {@link ApplicationScoped} context is initialized,
     * which typically occurs during the startup of the application server.</p>
     *
     * @param context the {@link ServletContext} that is initialized. This parameter is observed
     *                and injected automatically by CDI when the {@link ApplicationScoped} context is initialized.
     */
    public void initializedService(@Observes @Initialized(ApplicationScoped.class) ServletContext context) {
        // This method is intentionally left empty to trigger CDI bean instantiation
    }
}
