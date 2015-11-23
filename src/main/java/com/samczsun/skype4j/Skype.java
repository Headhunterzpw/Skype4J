/*
 * Copyright 2015 Sam Sun <me@samczsun.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.samczsun.skype4j;

import com.samczsun.skype4j.chat.Chat;
import com.samczsun.skype4j.chat.GroupChat;
import com.samczsun.skype4j.events.EventDispatcher;
import com.samczsun.skype4j.exceptions.ChatNotFoundException;
import com.samczsun.skype4j.exceptions.ConnectionException;
import com.samczsun.skype4j.exceptions.InvalidCredentialsException;
import com.samczsun.skype4j.exceptions.ParseException;
import com.samczsun.skype4j.user.Contact;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * This class represents a single Skype account, which may or may not have been logged in
 */
public abstract class Skype {

    /**
     * Subscribe to the HTTP long polling service
     * This will start reading events from Skype and calling events within this API
     *
     * @throws ConnectionException If an connection error occurs during subscription
     */
    public abstract void subscribe() throws ConnectionException;

    /**
     * Get the username of the account logged in
     *
     * @return The username
     */
    public abstract String getUsername();

    /**
     * Get a {@link Chat} based on the identity given. The chat must already be loaded
     * The identity is a Skype-assigned id that begins with {@code 19:} or {@code 8:}
     *
     * @param name The identity of the chat
     * @return The {@link Chat}, or null if not found
     */
    public abstract Chat getChat(String name);

    /**
     * Load a {@link Chat} given an identity
     * The identity is a Skype-assigned id that begins with {@code 19:} or {@code 8:}
     *
     * @param name The identity of the chat
     * @return The newly loaded {@link Chat}
     * @throws ConnectionException   If an error occurs during connection
     * @throws ChatNotFoundException If this skype account is not a member of the chat
     */
    public abstract Chat loadChat(String name) throws ConnectionException, ChatNotFoundException;

    /**
     * Get a chat, and if said chat doesn't exist, load it
     *
     * @param name The name of the chat
     * @return The chat
     * @throws ConnectionException If an exception occured while fetching chat details
     */
    public abstract Chat getOrLoadChat(String name) throws ConnectionException, ChatNotFoundException;

    /**
     * Get a contact based on the username. The contact must already be loaded
     *
     * @param username The username of the contact
     * @return The {@link Contact Contact} object, or null if not found
     */
    public abstract Contact getContact(String username);

    /**
     * Load a contact given a username
     *
     * @param username The username of the contact
     * @return The contact that was loaded
     * @throws ConnectionException If an exception occured while fetching contact details
     */
    public abstract Contact loadContact(String username) throws ConnectionException;

    /**
     * Get a contact, and if said contact doesn't exist, load it
     *
     * @param username The username of the contact
     * @return The contact
     * @throws ConnectionException If an exception occured while fetching contact details
     */
    public abstract Contact getOrLoadContact(String username) throws ConnectionException;

    /**
     * Get all the chats loaded by this API
     *
     * @return A view of all the chats
     */
    public abstract Collection<Chat> getAllChats();

    /**
     * Get all the contacts loaded by this API
     *
     * @return A view of all the chats
     */
    public abstract Collection<Contact> getAllContacts();

    /**
     * Log into Skype
     *
     * @throws InvalidCredentialsException If you've provided invalid credentials or if you hit a CAPTCHA
     * @throws ConnectionException         If a network error occured while connecting
     * @throws ParseException              If invalid HTML/XML was returned, causing Jsoup to raise an exception
     */
    public abstract void login() throws InvalidCredentialsException, ConnectionException, ParseException;

    /**
     * Log out and stop all threads
     *
     * @throws ConnectionException If an error occurs while logging out
     */
    public abstract void logout() throws ConnectionException;

    /**
     * Get the event dispatcher that handles listening to events
     *
     * @return The {@link EventDispatcher EventDispatcher}
     */
    public abstract EventDispatcher getEventDispatcher();

    /**
     * Get the Logger used for debugging
     *
     * @return The Logger
     */
    public abstract Logger getLogger();

    /**
     * Create a new group chat with the selected contacts. You will be automatically added to the group
     * If an error occurs while creating the chat, an {@link ConnectionException} or an {@link ChatNotFoundException} will be thrown
     *
     * @param contacts The contacts to add
     * @return The newly created group chat
     * @throws ConnectionException
     * @throws ChatNotFoundException
     */
    public abstract GroupChat createGroupChat(Contact... contacts) throws ConnectionException, ChatNotFoundException;
}
