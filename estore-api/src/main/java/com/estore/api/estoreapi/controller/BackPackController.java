package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.BackPack;
import com.estore.api.estoreapi.persistence.BackPackDAO;

@RestController
@RequestMapping("backpack")
public class BackPackController {
    private static final Logger LOG = Logger.getLogger(BackPackController.class.getName());
    private BackPackDAO backpackDao;

    public BackPackController(BackPackDAO backpackDao) {
        this.backpackDao = backpackDao;
    }

    /**
     * Responds to the GET request for all {@linkplain BackPack backpacks} whose
     * name
     * contains the text in name. If text is empty, returns all products.
     * 
     * @param locationOrActivity The location or activity parameter which contains the text used to find
     *                 the
     *                 {@link BackPack backpacks}
     * 
     * @return ResponseEntity with array of {@link BackPack backpack} objects (may
     *         be
     *         empty) and HTTP status of OK.
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise.
     */

    @GetMapping("")
    public ResponseEntity<BackPack[]> getBackPacks(@RequestParam(required = false) String locationOrActivity) {

        try {
            BackPack[] backpacks;
            if (locationOrActivity == null) {
                backpacks = backpackDao.getBackPacks();
            } else {
                backpacks = backpackDao.findBackPacks(locationOrActivity);
            }
            return new ResponseEntity<BackPack[]>(backpacks, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for a {@linkplain BackPack backpack} for the
     * given id
     *
     * @param id The id used to locate the {@link BackPack backpack}
     *
     * @return ResponseEntity with {@link BackPack backpack} object and HTTP status
     *         of OK if
     *         found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @GetMapping("/{id}")
    public ResponseEntity<BackPack> getBackPack(@PathVariable int id) {
        LOG.info("GET /backpacks/" + id);
        try {
            BackPack backpack = backpackDao.getBackPack(id);
            if (backpack != null)
                return new ResponseEntity<BackPack>(backpack, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<BackPack> updateBackPack(@RequestBody BackPack backpack) {
        LOG.info("PUT /backpack " + backpack);

        try {
            BackPack newBackPack = backpackDao.updateBackPack(backpack);
            if (newBackPack != null)
                return new ResponseEntity<BackPack>(newBackPack, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<BackPack> createBackPack(@RequestBody BackPack backpack) {
        LOG.info("POST /backpack " + backpack);
        try {
            BackPack[] existingBackPacks = backpackDao.findBackPacks(backpack.getName());
            if (existingBackPacks.length > 0) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                BackPack createdBackPack = backpackDao.createBackPack(backpack);
                return new ResponseEntity<BackPack>(createdBackPack, HttpStatus.CREATED);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a backpack with the given id
     * 
     * @param id The id of the {@link BackPack backpack} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<BackPack> deleteBackPack(@PathVariable int id) {
        LOG.info("DELETE /backpacks/" + id);
        try {
            if (backpackDao.deleteBackPack(id)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
