package com.amazon.checkerframework.cryptopolicy;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.amazon.checkerframework.cryptopolicy.qual.CryptoBlackListed;
import com.amazon.checkerframework.cryptopolicy.qual.CryptoWhiteListed;
import com.amazon.checkerframework.c