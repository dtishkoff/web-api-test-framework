package com.themoviedb.api.tests.services;

import com.themoviedb.api.tests.BaseAPITMDTest;
import org.junit.Test;

import java.io.IOException;

/**/

public class DiscoverIT extends BaseAPITMDTest{

    @Test
    public void testDiscoverMovies() throws IOException{
        discoverMovies();
    }
}
